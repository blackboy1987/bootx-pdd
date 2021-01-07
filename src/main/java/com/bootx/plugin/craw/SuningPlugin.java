/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.plugin.craw;

import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Plugin - FTP存储
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Component("suningPlugin")
public class SuningPlugin extends CrawlerPlugin {

	@Override
	public String getName() {
		return "苏宁易购";
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
				Element searchMain = root.getElementsByClass("search-main").first();
				Elements childrens = searchMain.children();
				for (Element child:childrens) {
					// 一级分类
					if(StringUtils.equalsIgnoreCase(child.tagName(),"div")&&child.attr("id")!=null){
						ProductCategory first = new ProductCategory();
						Element h2 = child.getElementsByTag("h2").first();
						first.setName(h2.text());
						first.setOtherId(child.attr("id"));
						first.setChildren(new HashSet<>());
						first.setPluginId(pluginConfig.getPluginId());
						Elements titleBoxs = child.getElementsByClass("title-box");
						// 二级分类
						for (Element titleBox:titleBoxs) {
							Element left = titleBox.getElementsByClass("t-left").first().getElementsByTag("a").first();
							ProductCategory second = new ProductCategory();
							second.setChildren(new HashSet<>());
							second.setName(left.text());
							second.setParent(first);
							second.setOtherUrl(left.attr("href"));
							second.setPluginId(pluginConfig.getPluginId());
							Elements rights = titleBox.getElementsByClass("t-right").first().getElementsByTag("a");
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
				}


			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return productCategories;
	}

	@Override
	public List<Product> search(String keywords) {
		return null;
	}

	@Override
	public Product product(String url) {
		Product product = new Product();
		// Document root = Jsoup.parse(new URL(url), 5000);
		Document root = Jsoup.parse(testUserHttpUnit(url,5000));
		// 商品名称
		String productName = root.getElementById("itemDisplayName").text();
		String mainprice = root.getElementsByClass("mainprice").first().text();
		product.setPrice(mainprice);
		product.setName(productName);
		product.setProductImages(productImages(root));
		product.setIntroduction(introduction(root));
		product.setParameterValues(parameterValues(root));
		product.setSkus(skus(root,product));
		product.setProductCategory(productCategory(root));
		return product;
	}

	private ProductCategory productCategory(Document root) {
		ProductCategory first = new ProductCategory();
		ProductCategory second = new ProductCategory();
		ProductCategory third = new ProductCategory();
		Elements headShopWrapper = root.getElementsByClass("head-shop-wrapper");
		if(headShopWrapper!=null&&headShopWrapper.size()>0){
			Elements aElements = headShopWrapper.first().getElementsByTag("a");
			if(aElements!=null&&aElements.size()>0){
				first.setName(aElements.get(0).text());
				if(aElements.get(1)!=null){
					second.setName(aElements.get(1).text());
					second.setParent(first);
				}
				if(aElements.get(2)!=null){
					third.setName(aElements.get(2).text());
					third.setParent(second);
				}
			}
		}
		return third;
	}

	private Set<Sku> skus(Document root,Product product) {
		Set<Sku> skus = new HashSet<>();
		// proinfo-buytype
		Elements proinfoBuytypes = root.getElementById("J-TZM").getElementsByClass("proattr-radio");
		List<Specification> specifications = new ArrayList<>();
		for (Element elment:proinfoBuytypes) {

			Elements dts = elment.getElementsByTag("dt");
			if (dts==null||dts.size()==0) {
				continue;
			}
			Element dt = dts.first();
			Elements lis = elment.getElementsByTag("dd");
			if (lis==null||lis.size()==0) {
				continue;
			}
			lis = lis.first().getElementsByClass("tip-infor");
			if(lis==null||lis.size()==0){
				continue;
			}
			lis = lis.first().getElementsByTag("li");
			if(lis==null||lis.size()==0){
				continue;
			}
			Specification specification = new Specification();
			specification.setName(dt.text());
			specification.setEntries(new ArrayList<>());
			specification.setOptions(new ArrayList<>());
			for (Element li:lis) {
				Specification.Entry entry = new Specification.Entry();
				Elements imgs = li.getElementsByTag("img");
				if(imgs!=null&&imgs.size()>0){
					// 有图片
					entry.setImg(imgs.first().attr("src"));
				}
				entry.setValue(li.attr("data-id"));
				entry.setName(li.text());
				specification.getEntries().add(entry);
				specification.getOptions().add(li.text());
			}
			specifications.add(specification);
		}
		List<String> result = new ArrayList<>();
		if(specifications.size()==1){
			result = descartes(specifications.get(0).getOptions());
		}else if(specifications.size()==2){
			result = descartes(specifications.get(0).getOptions(),specifications.get(1).getOptions());
		}else if(specifications.size()==3){
			result = descartes(specifications.get(0).getOptions(),specifications.get(1).getOptions(),specifications.get(2).getOptions());
		}else if(specifications.size()==4){
			result = descartes(specifications.get(0).getOptions(),specifications.get(1).getOptions(),specifications.get(2).getOptions(),specifications.get(3).getOptions());
		}
		product.setSpecifications(specifications);
		for (String item:result) {
			Sku sku = new Sku();
			sku.setSpecificationValues(new ArrayList<>());
			String [] items = item.split(";");
			for(int i=0;i<items.length;i++){
				SpecificationValue specificationValue = new SpecificationValue();
				specificationValue.setValue(items[i]);
				specificationValue.setId(i+"");
				sku.getSpecificationValues().add(specificationValue);
			}
			sku.setProduct(product);
			skus.add(sku);
		}
		return skus;
	}

	private List<ParameterValue> parameterValues(Document root) {
		List<ParameterValue> parameterValues = new ArrayList<>();
		Element proconParam = root.getElementsByClass("procon-param").first();
		// bzqd_tag
		Element table = proconParam.getElementById("bzqd_tag");
		Elements trs = table.getElementsByTag("tr");
		Integer index = 1;
		for (Element tr:trs) {
			if(index==1){
				// 参数组的名字
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.setGroup(tr.text());
				parameterValue.setEntries(new ArrayList<>());
				parameterValues.add(parameterValue);
			}else{
				ParameterValue parameterValue = parameterValues.get(parameterValues.size()-1);
				Elements tds = tr.getElementsByTag("td");
				parameterValue.getEntries().add(new ParameterValue.Entry(tds.get(0).text(),tds.get(1).text()));
			}
			index++;
		}

		// itemParameter
		Element itemParameter = proconParam.getElementById("itemParameter");
		Elements tr1s = itemParameter.getElementsByTag("tr");
		for (Element tr:tr1s) {
			if(StringUtils.isBlank(tr.attr("parametercode"))){
				ParameterValue parameterValue1 = new ParameterValue();
				parameterValue1.setGroup(tr.text());
				parameterValue1.setEntries(new ArrayList<>());
				parameterValues.add(parameterValue1);
			}else{
				ParameterValue parameterValue1 = parameterValues.get(parameterValues.size()-1);
				Elements tds = tr.getElementsByTag("td");
				parameterValue1.getEntries().add(new ParameterValue.Entry(tds.get(0).text(),tds.get(1).text()));
			}
		}
		return parameterValues;
	}

	private String introduction(Document root) {
		return root.getElementById("productDetail").html();
	}


	private List<ProductImage> productImages(Document root){
		// imgzoom-thumb-main 商品图片
		Element imgzoomThumbMain = root.getElementsByClass("imgzoom-thumb-main").first();
		Elements imgElements = imgzoomThumbMain.getElementsByTag("img");
		return imgElements.stream().map(item->{
			ProductImage productImage = new ProductImage();
			String larget = item.attr("src-large");
			String medium = item.attr("src-medium");
			String src = item.attr("src");
			productImage.setLarge(larget);
			productImage.setMedium(medium);
			productImage.setSource(src);
			productImage.setThumbnail(medium);
			return productImage;
		}).collect(Collectors.toList());


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