
package com.bootx.plugin;

import com.bootx.entity.Member;
import com.bootx.entity.PluginConfig;
import com.bootx.entity.Product;
import com.bootx.entity.ProductCategory;
import com.bootx.service.PluginConfigService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

;

/**
 * Plugin - 存储
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public abstract class CrawlerPlugin implements Comparable<CrawlerPlugin> {

	@Autowired
	private PluginConfigService pluginConfigService;

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public String getId() {
		return getClass().getAnnotation(Component.class).value();
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public abstract String getName();

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	public abstract String getVersion();

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	public abstract String getAuthor();

	/**
	 * 获取网址
	 * 
	 * @return 网址
	 */
	public abstract String getSiteUrl();

	/**
	 * 获取安装URL
	 * 
	 * @return 安装URL
	 */
	public abstract String getInstallUrl();

	/**
	 * 获取卸载URL
	 * 
	 * @return 卸载URL
	 */
	public abstract String getUninstallUrl();

	/**
	 * 获取设置URL
	 * 
	 * @return 设置URL
	 */
	public abstract String getSettingUrl();

	/**
	 * 获取是否已安装
	 * 
	 * @return 是否已安装
	 */
	public boolean getIsInstalled() {
		return pluginConfigService.pluginIdExists(getId());
	}

	/**
	 * 获取插件配置
	 * 
	 * @return 插件配置
	 */
	public PluginConfig getPluginConfig() {
		return pluginConfigService.findByPluginId(getId());
	}

	/**
	 * 获取是否已启用
	 * 
	 * @return 是否已启用
	 */
	public boolean getIsEnabled() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getIsEnabled() : false;
	}

	/**
	 * 获取属性值
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性值
	 */
	public String getAttribute(String name) {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(name) : null;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public Integer getOrder() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getOrder() : null;
	}


	public abstract List<ProductCategory> productCategory();

	public abstract Product product(Member member,String url);

	public abstract List<Product> search(String keywords);

	/**
	 * 获取访问URL
	 * 
	 * @param path
	 *            上传路径
	 * @return 访问URL
	 */
	public abstract String getUrl(String path);

	/**
	 * 实现compareTo方法
	 * 
	 * @param crawlerPlugin
	 *            存储插件
	 * @return 比较结果
	 */
	@Override
	public int compareTo(CrawlerPlugin crawlerPlugin) {
		if (crawlerPlugin == null) {
			return 1;
		}
		return new CompareToBuilder().append(getOrder(), crawlerPlugin.getOrder()).append(getId(), crawlerPlugin.getId()).toComparison();
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		CrawlerPlugin other = (CrawlerPlugin) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	/**
	 * 重写hashCode方法
	 * 
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}


	public String testUserHttpUnit(String url,Integer timeout){

		try{
			//构造一个webClient 模拟Chrome 浏览器
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
//屏蔽日志信息
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
					"org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
//支持JavaScript
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(timeout);
			HtmlPage rootPage = webClient.getPage(url);
//设置一个运行JavaScript的时间
			webClient.waitForBackgroundJavaScript(5000);
			String html = rootPage.asXml();
			html = new String(html.getBytes(), Charset.forName("gbk"));
			return html;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> descartes(List<String>... lists) {
		List<String> tempList = new ArrayList<>();
		for (List<String> list : lists) {
			if (tempList.isEmpty()) {
				tempList = list;
			} else {
				tempList = tempList.stream().flatMap(item -> list.stream().map(item2 -> item + ";" + item2)).collect(Collectors.toList());
			}
		}
		return tempList;
	}
}