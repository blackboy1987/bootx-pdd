/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.plugin.craw;

import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.jd.JsonRootBean;
import com.bootx.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Plugin - FTP存储
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Component("jdPlugin")
public class JdPlugin extends CrawlerPlugin {

	@Override
	public String getName() {
		return "京东";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "SHOP++";
	}

	@Override
	public String getSiteUrl() {
		return "http://www.bootx.net";
	}

	@Override
	public String getInstallUrl() {
		return "ftp_storage/install";
	}

	@Override
	public String getUninstallUrl() {
		return "ftp_storage/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "ftp_storage/setting";
	}

	@Override
	public List<ProductCategory> productCategory() {
		PluginConfig pluginConfig = getPluginConfig();
		List<ProductCategory> productCategories = new ArrayList<>();
		if (pluginConfig != null) {
			String url = pluginConfig.getAttribute("category");
			try{
				Document root = Jsoup.parse(new URL(url), 5000);
				Element searchMain = root.getElementsByClass("category-items").first();
				Elements childrens = searchMain.getElementsByClass("category-item");
				for (Element child:childrens) {
					Element firstElement = child.getElementsByClass("mt").first();
					ProductCategory first = new ProductCategory();
					first.setName(firstElement.text());
					first.setChildren(new HashSet<>());
					first.setPluginId(pluginConfig.getPluginId());
					Elements secondElements = child.getElementsByClass("mc").first().getElementsByClass("items").first().getElementsByTag("dl");
					// 二级分类
					for (Element titleBox:secondElements) {
						Element left = titleBox.getElementsByTag("dt").first().getElementsByTag("a").first();
						ProductCategory second = new ProductCategory();
						second.setChildren(new HashSet<>());
						second.setName(left.text());
						second.setParent(first);
						second.setOtherUrl(left.attr("href"));
						second.setPluginId(pluginConfig.getPluginId());
						Elements rights = titleBox.getElementsByTag("dd").first().getElementsByTag("a");
						// 三级分类
						for (Element right:rights) {
							ProductCategory third = new ProductCategory();
							third.setChildren(new HashSet<>());
							third.setName(right.text());
							third.setParent(second);
							third.setOtherUrl(right.attr("href"));
							third.setPluginId(pluginConfig.getPluginId());
							second.getChildren().add(third);
						}
						first.getChildren().add(second);
					}

					productCategories.add(first);
				}


			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return productCategories;
	}

	@Override
	public Product product(String url) {
		String html = testUserHttpUnit(url);
		Product product = new Product();
		Document root = Jsoup.parse(html);
		Elements elements = root.select("script[charset='gbk']");
		if(elements!=null&&elements.size()>0){
			String data = elements.first().data();
			System.out.println(data);
			data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
			System.out.println(data);
			data = data.split("varpageConfig=")[1].split(";try")[0];
			System.out.println(data);

			product.setParameterValues(parameterValues(root,product));
			product.setSpecifications(specifications(root));
			product.setIntroduction(introduction(root,product));
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try{
				Object eval = engine.eval("JSON.stringify(" + data + ")");
				System.out.println(eval);
				JsonRootBean jsonRootBean = JsonUtils.toObject(eval.toString(),JsonRootBean.class);
				product.setName(jsonRootBean.getProduct().getName());
				product.setProductImages(productImages(jsonRootBean));
				product.setSkus(skus(jsonRootBean,product));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return product;
	}

	private String introduction(Document root, Product product) {
		product.setIntroductionImages(new ArrayList<>());
		Element content = root.getElementById("J-detail-content");
		if(content!=null){
			Elements imgs = content.getElementsByTag("img");
			if(imgs!=null){
				imgs.stream().forEach(img->{
					product.getIntroductionImages().add(img.attr("src"));
				});
			}


			return content.html();
		}
		return "";

	}

	private List<ParameterValue> parameterValues(Document root, Product product) {
		List<ParameterValue> parameterValues = new ArrayList<>();
		Elements elements = root.getElementsByClass("p-parameter");
		if(elements!=null){
			AtomicReference<Integer> index = new AtomicReference<>(0);
			elements.stream().forEach(item->{
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.setEntries(new ArrayList<>());
				parameterValue.setGroup("参数"+(index.getAndSet(index.get() + 1)));
				Elements elements1 = item.getElementsByTag("li");
				if(elements1!=null){
					elements1.stream().forEach(item1->{
						String text = item1.text();
						if(StringUtils.isNotBlank(text)){
							String[] texts = text.split(":");
							if(texts.length==2){
								parameterValue.getEntries().add(new ParameterValue.Entry(texts[0],texts[1]));
							}
						}
					});
				}
				if(parameterValue.getEntries().size()>0){
					parameterValues.add(parameterValue);
				}
			});
		}
		return parameterValues;
	}

	private List<Specification> specifications(Document root) {
		Element chooseAttrs = root.getElementById("choose-attrs");
		List<Specification> specifications = new ArrayList<>();
		if(chooseAttrs!=null){
			Elements pChooses = chooseAttrs.getElementsByClass("p-choose");
			Integer order = 0;
			for (Element pChoose:pChooses){
				Specification specification = new Specification();
				specification.setOrder(order++);
				specification.setName(pChoose.attr("data-type"));
				specification.setEntries(new ArrayList<>());
				specification.setOptions(new ArrayList());
				Elements dds = pChoose.getElementsByClass("dd");
				if(dds!=null&&dds.size()>0){
					Elements items = dds.first().getElementsByClass("item");
					if(items!=null&&items.size()>0){
						Integer order1 = 0;
						items.stream().forEach(item->{
							specification.getOptions().add(item.text());
							Elements imgs = item.getElementsByTag("img");
							if (imgs!=null&&imgs.size()>0) {
								specification.getEntries().add(new Specification.Entry(item.text(),(order1+1)+"",imgs.first().attr("src")));
							}else{
								specification.getEntries().add(new Specification.Entry(item.text(),(order1+1)+"",null));
							}
						});
					}
				}
			}
		}
		return specifications;
	}

	private Set<Sku> skus(JsonRootBean jsonRootBean,Product product) {
		List<Map<String,String>> colorSizes = jsonRootBean.getProduct().getColorSize();
		Set<Sku> skus = new HashSet<>();
		Map<String,Integer> map = new HashMap<>();;
		product.getSpecifications().stream().forEach(item->{
			map.put(item.getName(),item.getOrder());
		});
		colorSizes.stream().forEach(item->{
			Sku sku = new Sku();
			sku.setSpecificationValues(new ArrayList<>());
			for (String key:item.keySet()) {
				SpecificationValue specificationValue = new SpecificationValue();
				if(StringUtils.equals("skuId", key)){
					sku.setSn(item.get("skuId"));
				}else{
					specificationValue.setName(key);
					specificationValue.setValue(item.get(key));
					specificationValue.setId(map.get(key)+"");
				}
				sku.getSpecificationValues().add(specificationValue);
			}
			skus.add(sku);
		});
		return skus;
	}

	private List<ProductImage> productImages(JsonRootBean jsonRootBean) {
		AtomicReference<Integer> order = new AtomicReference<>(0);
		return jsonRootBean.getProduct().getImageList().stream().map(img->{
			ProductImage productImage = new ProductImage();
			productImage.setLarge(img);
			productImage.setMedium(img);
			productImage.setSource(img);
			productImage.setThumbnail(img);
			productImage.setOrder(order.getAndSet(order.get() + 1));
			return productImage;
		}).collect(Collectors.toList());


	}

	@Override
	public List<Product> search(String keywords) {
		return null;
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

}