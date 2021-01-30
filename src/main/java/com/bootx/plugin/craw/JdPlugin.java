
package com.bootx.plugin.craw;
import com.bootx.entity.*;
import com.bootx.plugin.CrawlerPlugin;
import com.bootx.plugin.craw.pojo.jd.JsonRootBean;
import com.bootx.plugin.craw.pojo.jd.Product;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
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
	public CrawlerProduct product(Member member, CrawlerProduct crawlerProduct) {

		try{
			Document root = Jsoup.parse((crawlerProduct.getUrl()));
			JsonRootBean jsonRootBean = jsonRootBean(root);
			Product product = jsonRootBean.getProduct();
			crawlerProduct.setSn(product.getSkuid()+"");
			crawlerProduct.setName(product.getName());
			crawlerProduct.setPrice(price(root,crawlerProduct));
			productImages(member,jsonRootBean,crawlerProduct);
			specifications(member,root,crawlerProduct);
			parameterValues(member,root,crawlerProduct);
			introduction(member,jsonRootBean,crawlerProduct);
			skus(member,jsonRootBean,crawlerProduct);
		}catch (Exception e){
			e.printStackTrace();
		}
		return crawlerProduct;
	}

	/**
	 * 解析标题
	 * @param root
	 * @param product
	 * @return
	 */
	public String price(Document root, CrawlerProduct product) {
		try {
			Element titleElement = root.getElementsByClass("summary-price").first();
			return titleElement.getElementsByClass("price").text();
		}catch (Exception e){
			e.printStackTrace();
			return "无价格";
		}
	}

	/**
	 * 解析主图
	 * @param root
	 * @return
	 */
	private void productImages(Member member, JsonRootBean root, CrawlerProduct crawlerProduct) {
		crawlerProduct.getCrawlerProductImage().setImages(root.getProduct().getImageList().stream().map(item->"http://img12.360buyimg.com/n1"+item).collect(Collectors.toList()));
	}
	/**
	 * 解析规格
	 * @return
	 */
	public CrawlerProductSpecification specifications(Member member, Document root, CrawlerProduct crawlerProduct) {
		CrawlerProductSpecification crawlerProductSpecification = crawlerProduct.getCrawlerProductSpecification();
		crawlerProductSpecification.setCrawlerSpecifications(new ArrayList<>());
		Element chooseAttrs = root.getElementById("choose-attrs");
		List<CrawlerSpecification> specifications = new ArrayList<>();
		if(chooseAttrs!=null){
			Elements pChooses = chooseAttrs.getElementsByClass("p-choose");
			for (Element pChoose:pChooses){
				CrawlerSpecification specification = new CrawlerSpecification();
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
								specification.getEntries().add(new CrawlerSpecification.Entry(item.attr("data-value"),(order1+1)+"",imgs.first().attr("src")));
							}else{
								specification.getEntries().add(new CrawlerSpecification.Entry(item.text(),(order1+1)+"",null));
							}
						}
					}
				}
				crawlerProductSpecification.getCrawlerSpecifications().add(specification);
			}
		}
		return crawlerProductSpecification;
	}

	/**
	 * 商品属性
	 * @param root
	 * @param crawlerProduct
	 * @return
	 */
	public CrawlerProductParameterValue parameterValues(Member member,Document root, CrawlerProduct crawlerProduct) {
		CrawlerProductParameterValue crawlerProductParameterValue = crawlerProduct.getCrawlerProductParameterValue();
		crawlerProductParameterValue.setParameterValues(new ArrayList<>());
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
					crawlerProductParameterValue.getParameterValues().add(parameterValue);
				}
			}
		}
		crawlerProduct.setCrawlerProductParameterValue(crawlerProductParameterValue);
		return crawlerProduct.getCrawlerProductParameterValue();
	}

	/**
	 * 解析详情和详情图
	 * @param jsonRootBean
	 * @return
	 */
	private String introduction(Member member, JsonRootBean jsonRootBean, CrawlerProduct crawlerProduct) {
		String url = jsonRootBean.getProduct().getDesc();
		String result = url(url);
		Map<String, String> map = JsonUtils.toObject(result, new TypeReference<Map<String, String>>() {});
		String content = map.get("content");
		Elements imgs = Jsoup.parse(content).getElementsByTag("img");
		if(imgs!=null){
			crawlerProduct.getCrawlerProductIntroductionImage().setImages(imgs.stream().map(img->img.attr("src")).collect(Collectors.toList()));
		}
		return Jsoup.parse(content).text();
	}

	public CrawlerProductSku skus(Member member, JsonRootBean jsonRootBean, CrawlerProduct crawlerProduct) {
		CrawlerProductSku crawlerProductSku = crawlerProduct.getCrawlerProductSku();
		crawlerProductSku.setSkus(new ArrayList<>());

		List<Map<String,String>> colorSizes = jsonRootBean.getProduct().getColorSize();
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
						specificationValue.setId(item.get(key)+"");
					}
					sku.getSpecificationValues().add(specificationValue);
				}
				crawlerProductSku.getSkus().add(sku);
			});
		}else{
			Sku sku = new Sku();
			sku.setSpecificationValues(new ArrayList<>());
			sku.setSn(crawlerProduct.getSn());
			crawlerProductSku.getSkus().add(sku);
		}
		return crawlerProductSku;
	}


	private JsonRootBean jsonRootBean(Document root) {
		Elements elements = root.select("script[charset='gbk']");
		if(elements!=null&&elements.size()>0) {
			String data = elements.first().data();
			System.out.println(data);
			data = data.replace(" ", "").replace("\n", "").replace("\r", "").replace("\r\n", "");
			System.out.println(data);
			data = data.split("varpageConfig=")[1].split(";try")[0];
			System.out.println(data);
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				Object eval = engine.eval("JSON.stringify(" + data + ")");
				try {
					com.bootx.plugin.craw.pojo.jd.JsonRootBean jsonRootBean = JsonUtils.toObject(eval.toString(), com.bootx.plugin.craw.pojo.jd.JsonRootBean.class);
					System.out.println(jsonRootBean.getProduct());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<CrawlerProduct> search(String keywords) {
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


	public static String url(String url) {
		if(!StringUtils.startsWith(url,"http:")&&!StringUtils.startsWith(url,"https:")){
			url = "http:"+url;
		}
		Map<String,Object> headers = new HashMap<>();
		headers.put("cookie","__jdu=16076610028271629515156; shshshfpa=847975b7-73a2-0529-8e61-90c57103ff98-1607661005; shshshfpb=o3sXhcIu8RMjLqwU4W7D%20vQ%3D%3D; user-key=6eeed015-6181-4e48-b4be-e90b01130beb; cn=0; unpl=V2_ZzNtbREDQhMmCUNWexEMUWIKEV8SAhQRcw5FXStMWQ1lVxtYclRCFnUUR1RnGFsUZwYZWURcQxVFCEdkeB5fA2AFEFlBZxpFK0oYEDlNDEY1WnxZRldAFXEIQFx8KWwGZzMSXHJXRBRzD0FUehhfDGQFElpBX0cVcQhCU0spWzVXMxJfQ1JLHUUJdlVLWwhZYgIRXkJeDhVyCUBTfBldBGQKEVtCUEAdcQhCVH8ebARXAA%3d%3d; areaId=15; __jdv=76161171|direct|-|none|-|1611323641292; __jda=122270672.16076610028271629515156.1607661002.1610698375.1611323641.20; __jdc=122270672; shshshfp=8f1072e164f7efede3844f10c5a49ba4; ipLoc-djd=15-1158-46341-46352; 3AB9D23F7A4B3C9B=L6MCANK2GWKFO6RYAP4R74OMKOAPS42ULYCP2JNRIOTAJXA35BIY32CCJVWHNWW2PRUU4SR5OO3B7LXU7E7UOKWK6E; shshshsID=ce77f11fb82b0effff432790cd29f1a2_9_1611324032907; __jdb=122270672.12.16076610028271629515156|20.1611323641");
		headers.put("sec-fetch-mode"," navigate");
		headers.put("user-agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.66");
		String result = WebUtils.get(url,null,headers);
		return result;
	}
}