/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.plugin.craw;
import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.jd.JsonRootBean;
import com.bootx.plugin.craw.pojo.jd.JsonRootBean1;
import com.bootx.util.DateUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.UploadUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FilenameUtils;
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
							String[] split = third.getOtherUrl().split("cat=");
							if(split.length==2){
								split = split[1].split(",");
								if(split.length==3){
									third.setOtherId(pluginConfig.getPluginId()+"_"+split[2]);
									second.setOtherId(pluginConfig.getPluginId()+"_"+split[1]);
									first.setOtherId(pluginConfig.getPluginId()+"_"+split[0]);
								}
							}
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
	public Product product(Member member,String url) {
		String html = testUserHttpUnit(url,3000);
		if(StringUtils.isBlank(html)){
			return null;
		}
		Product product = new Product();

		product.setUrl(url);
		Document root = Jsoup.parse(html);
		Elements elements = root.select("script[charset='gbk']");
		product.getProductParameterValue().setParameterValues(parameterValues(root,product));
		product.setSpecifications(specifications(root,product));
		product.setProductCategoryNames(productCategoryNames(root,product));
		product.setProductStore(productStore(root,product));
		productImages(member,root,product);
		if(elements!=null&&elements.size()>0){
			String data = elements.first().data();
			System.out.println(data);
			data = data.replace(" ","").replace("\n","").replace("\r","").replace("\r\n","");
			System.out.println(data);
			data = data.split("varpageConfig=")[1].split(";try")[0];
			System.out.println(data);
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try{
				Object eval = engine.eval("JSON.stringify(" + data + ")");
				JsonRootBean1 jsonRootBean1 = JsonUtils.toObject(eval.toString(),JsonRootBean1.class);
				JsonRootBean jsonRootBean = JsonUtils.toObject(JsonUtils.toJson(jsonRootBean1),JsonRootBean.class);
				try {
					jsonRootBean = JsonUtils.toObject(eval.toString(),JsonRootBean.class);
				}catch (Exception e){
					e.printStackTrace();
				}
				productCategoryIds(jsonRootBean,product);
				productCategoryIds(jsonRootBean,product);
				product.setName(jsonRootBean.getProduct().getName());
				product.getProductSku().setSkus(skus(jsonRootBean,product));
				productImages(member,jsonRootBean,product);
				product.getProductIntroduction().setContent(introduction(member,jsonRootBean,product));
				product.setSn(getPluginConfig().getPluginId()+"_"+jsonRootBean.getProduct().getSkuid()+"");
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			productCategoryIds(root,product);
			product.setName(name(root));
			product.getProductSku().setSkus(skus(root,product));
			productImages(member,root,product);
			product.getProductIntroduction().setContent(introduction(member,root,product));
			product.setSn(getPluginConfig().getPluginId()+"_"+sn(url,product));
		}
		return product;

	}

	private String sn(String url, Product product) {
		String sn = url.split(".html")[0];
		sn = sn.substring(sn.lastIndexOf("/"));
		return sn;
	}

	private String name(Document root) {
		Elements skuNames = root.getElementsByClass("sku-name");
		if(skuNames!=null&&skuNames.size()>0){
			return skuNames.first().text();
		}
		return "";
	}

	private ProductStore productStore(Document root, Product product) {
		ProductStore productStore = new ProductStore();
		Element popbox = root.getElementById("popbox");
		if(popbox!=null){
			Elements h3 = popbox.getElementsByTag("h3");
			if(h3!=null&&h3.size()>0){
				Elements a = h3.first().getElementsByTag("a");
				if(a!=null&&a.size()>0){
					Element first = a.first();
					productStore.setUrl(first.attr("href"));
					productStore.setName(first.text());
				}
			}
		}
		return productStore;
	}

	private List<String> productCategoryNames(Document root, Product product) {
		List<String> names = new ArrayList<>();
		Element crumbWrap = root.getElementById("crumb-wrap");
		if(crumbWrap!=null){
			Elements crumbs = crumbWrap.getElementsByClass("crumb");
			if(crumbs!=null&&crumbs.size()>0){
				names = crumbs.first().getElementsByClass("item").stream().map(item->item.text()).collect(Collectors.toList());
			}
		}
		return names;

	}

	private void productCategoryIds(JsonRootBean jsonRootBean, Product product) {
		List<Long> cats = jsonRootBean.getProduct().getCat();
		product.setProductCategoryIds(cats);
	}

	private void productCategoryIds(Document root, Product product) {

		List<String> names = new ArrayList<>();
		Element crumbWrap = root.getElementById("crumb-wrap");
		if(crumbWrap!=null){
			Elements crumbs = crumbWrap.getElementsByClass("crumb");
			if(crumbs!=null&&crumbs.size()>0){
				Elements as = crumbs.first().getElementsByClass("a");
				if(as!=null&&as.size()>=3){
					Element element = as.get(3);
					System.out.println(element);
				}
			}
		}
	}

	private String introduction(Member member,JsonRootBean root, Product product) {
		ProductIntroductionImage productIntroductionImage = new ProductIntroductionImage();
		productIntroductionImage.setImages(new ArrayList<>());
		String desc = root.getProduct().getDesc();
		String html = WebUtils.get(StringUtils.startsWith(desc,"http")?desc:"http:"+desc,null);
		Map<String, String> map = JsonUtils.toObject(html, new TypeReference<Map<String, String>>() {
		});
		if(map!=null&&StringUtils.isNotBlank(map.get("content"))){
			Elements imgs = Jsoup.parse(map.get("content")).getElementsByTag("img");
			if(imgs!=null){
				imgs.stream().forEach(img->{

					String url = img.attr("data-lazyload");
					String extension = FilenameUtils.getExtension(url);
					String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
					UploadUtils.upload(url,path);
					url = UploadUtils.getUrl(path);
					productIntroductionImage.getImages().add(url);
				});
			}
			productIntroductionImage.setProduct(product);
			product.setProductIntroductionImage(productIntroductionImage);
			return map.get("content");
		}
		return "";

	}

	private String introduction(Member member,Document root, Product product) {
		ProductIntroductionImage productIntroductionImage = new ProductIntroductionImage();
		productIntroductionImage.setImages(new ArrayList<>());
		String desc = root.getElementById("J-detail-content").html();
		if(StringUtils.isNotBlank(desc)){
			Elements imgs = Jsoup.parse(desc).getElementsByTag("img");
			if(imgs!=null){
				imgs.stream().forEach(img->{
					String url = img.attr("data-lazyload");
					String extension = FilenameUtils.getExtension(url);
					String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
					UploadUtils.upload(url,path);
					url = UploadUtils.getUrl(path);
					productIntroductionImage.getImages().add(url);
				});
			}
			productIntroductionImage.setProduct(product);
			product.setProductIntroductionImage(productIntroductionImage);
			return desc;
		}
		return "";

	}

	private List<ParameterValue> parameterValues(Document root, Product product) {
		List<ParameterValue> parameterValues = new ArrayList<>();
		Elements elements = root.getElementsByClass("p-parameter-list");
		if(elements!=null){
			AtomicReference<Integer> index = new AtomicReference<>(0);
			for (Element item:elements) {
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.setEntries(new ArrayList<>());
				parameterValue.setGroup("参数"+(index.getAndSet(index.get() + 1)));
				Elements elements1 = item.getElementsByTag("li");
				if(elements1!=null){
					for (Element item1:elements1) {
						String text = item1.text();
						if(StringUtils.isNotBlank(text)){
							String[] texts = text.split("：");
							if(texts.length==2){
								parameterValue.getEntries().add(new ParameterValue.Entry(texts[0],texts[1]));
							}
						}
					}
				}
				if(parameterValue.getEntries().size()>0){
					parameterValues.add(parameterValue);
				}
			}
		}
		return parameterValues;
	}

	private List<Specification> specifications(Document root,Product product) {
		Element chooseAttrs = root.getElementById("choose-attrs");
		List<Specification> specifications = new ArrayList<>();
		if(chooseAttrs!=null){
			Elements pChooses = chooseAttrs.getElementsByClass("p-choose");
			for (Element pChoose:pChooses){
				Specification specification = new Specification();
				specification.setName(pChoose.attr("data-type"));
				specification.setEntries(new ArrayList<>());
				specification.setOptions(new ArrayList());
				Elements dds = pChoose.getElementsByClass("dd");
				if(dds!=null&&dds.size()>0){
					Elements items = dds.first().getElementsByClass("item");
					if(items!=null&&items.size()>0){
						Integer order1 = 0;
						for (Element item:items) {
							specification.getOptions().add(item.attr("data-value"));
							Elements imgs = item.getElementsByTag("img");
							if (imgs!=null&&imgs.size()>0) {
								specification.getEntries().add(new Specification.Entry(item.attr("data-value"),(order1+1)+"",imgs.first().attr("src")));
							}else{
								specification.getEntries().add(new Specification.Entry(item.text(),(order1+1)+"",null));
							}
						}
					}
				}
				specifications.add(specification);
			}
		}
		return specifications;
	}

	private List<Sku> skus(JsonRootBean jsonRootBean,Product product) {
		List<Map<String,String>> colorSizes = jsonRootBean.getProduct().getColorSize();
		List<Sku> skus = new ArrayList<>();
		Map<String,Integer> map = new HashMap<>();;
		Integer order = 1;
		for (Specification item:product.getSpecifications()) {
			map.put(item.getName(),order++);
		}
		if(colorSizes!=null&&colorSizes.size()>0){
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
		}else{
			Sku sku = new Sku();
			sku.setSpecificationValues(new ArrayList<>());
			sku.setSn(product.getSn());
			skus.add(sku);
		}
		return skus;
	}

	private List<Sku> skus(Document root,Product product) {
		List<Sku> skus = new ArrayList<>();
		Map<String,Integer> map = new HashMap<>();
		Integer order = 1;
		for (Specification item:product.getSpecifications()) {
			map.put(item.getName(),order++);
		}

		List<List<String>> list = product.getSpecifications().stream().map(item->item.getOptions()).collect(Collectors.toList());
		List<String> colorSizes = new ArrayList<>();
		if(list.size()==1){
			colorSizes = descartes(list.get(0));
		}else if(list.size()==2){
			colorSizes = descartes(list.get(0),list.get(1));
		}else if(list.size()==3){
			colorSizes = descartes(list.get(0),list.get(1),list.get(2));
		}else if(list.size()==4){
			colorSizes = descartes(list.get(0),list.get(1),list.get(2),list.get(3));
		}

		for (String colorSize:colorSizes) {
			Sku sku = new Sku();
			sku.setSpecificationValues(new ArrayList<>());
			String[] keys = colorSize.split(";");
			for (int i=0;i<keys.length;i++){
				SpecificationValue specificationValue = new SpecificationValue();
				specificationValue.setName(product.getSpecifications().get(i).getName());
				specificationValue.setValue(keys[i]);
				specificationValue.setId(i+"");
				sku.getSpecificationValues().add(specificationValue);
			}
			skus.add(sku);
		}
		return skus;
	}


	private void productImages(Member member,Document root,Product product) {
		if(product.getProductImages().size()>0){
			return;
		}
		AtomicReference<Integer> order = new AtomicReference<>(0);
		Element specList = root.getElementById("spec-list");
		if(specList!=null){
			Elements imgs = specList.getElementsByTag("img");
			if(imgs!=null){
				product.setProductImages(imgs.stream().map(img->{
					ProductImage productImage = new ProductImage();
					String url = getAttribute("largeImageUrlPrefix")+img.attr("data-url");
					String extension = FilenameUtils.getExtension(url);
					String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
					UploadUtils.upload(url,path);
					url = UploadUtils.getUrl(path);
					productImage.setLarge(url+"?x-oss-process=style/800");
					productImage.setMedium(url+"?x-oss-process=style/480");
					productImage.setSource(url);
					productImage.setThumbnail(url+"?x-oss-process=style/65");
					productImage.setOrder(order.getAndSet(order.get() + 1));
					return productImage;
				}).collect(Collectors.toList()));
			}
		}
	}

	private void productImages(Member member,JsonRootBean jsonRootBean,Product product) {
		AtomicReference<Integer> order = new AtomicReference<>(0);
		product.setProductImages(jsonRootBean.getProduct().getImageList().stream().map(img->{
			ProductImage productImage = new ProductImage();

			String url = getAttribute("largeImageUrlPrefix")+img;
			String extension = FilenameUtils.getExtension(url);
			String path = member.getUsername()+"/image/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd")+"/"+UUID.randomUUID().toString().replace("-","")+"."+extension;
			UploadUtils.upload(url,path);
			url = UploadUtils.getUrl(path);
			productImage.setLarge(url+"?x-oss-process=style/800");
			productImage.setMedium(url+"?x-oss-process=style/480");
			productImage.setSource(url);
			productImage.setThumbnail(url+"?x-oss-process=style/65");
			productImage.setOrder(order.getAndSet(order.get() + 1));
			return productImage;
		}).collect(Collectors.toList()));
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