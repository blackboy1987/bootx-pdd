/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.plugin.craw;

import com.bootx.entity.PluginConfig;
import com.bootx.entity.Product;
import com.bootx.entity.ProductCategory;
import com.bootx.plugin.CrawlerPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
		return null;
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