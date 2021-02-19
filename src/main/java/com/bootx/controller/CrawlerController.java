package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.ProductCategory;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.service.PddCrawlerProductService;
import com.bootx.security.CurrentUser;
import com.bootx.service.CrawlerProductService;
import com.bootx.service.MemberService;
import com.bootx.service.ProductCategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author black
 */
@RestController("pddCrawlerController")
@RequestMapping("/pdd/crawler")
@CrossOrigin
public class CrawlerController extends BaseController {

    @Resource
    private CrawlerProductService crawlerProductService;
    @Resource
    private PddCrawlerProductService pddCrawlerProductService;

    @Resource
    private MemberService memberService;
    @Resource
    private ProductCategoryService productCategoryService;

    /**
     * 多个链接的采集
     * @param urls
     * @param type
     * @param member
     * @param request
     * @return
     */
    @PostMapping
    public Result crawler(String[] urls, Integer type, @CurrentUser Member member, HttpServletRequest request){
        if(urls==null||urls.length<=0){
            return Result.error("请输入抓取地址");
        }
        // 判断地址对不对的问题
        for (int i=0;i<urls.length;i++){
            if(!checkUrl(urls[i])){
                return Result.error("第"+(i+1)+"条抓取地址不正确");
            }
        }

        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("请先登录");
        }
        String batchId = member.getId()+"_"+System.currentTimeMillis();
        crawlerProductService.crawler(member,urls,type,batchId);
        return Result.success(batchId);
    }

    private boolean checkUrl(String url) {
        // 淘宝：item.taobao.com
        if (StringUtils.contains(url,"item.taobao.com")){
            return true;
        }
        // 天猫：detail.tmall.com
        if (StringUtils.contains(url,"detail.tmall.com")){
            return true;
        }
        return false;
    }

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(String batchId, @CurrentUser Member member, HttpServletRequest request){
        List<PddCrawlerProduct> pddCrawlerProducts = pddCrawlerProductService.findList1(batchId);
        for (PddCrawlerProduct pddCrawlerProduct:pddCrawlerProducts) {
            pddCrawlerProduct.setProductCategories(tuiJianCategory(pddCrawlerProduct.getName()));
        }

        return Result.success(pddCrawlerProducts);
    }



    @PostMapping("/detail")
    @JsonView(BaseEntity.EditView.class)
    public Result detail(Long[] productIds, @CurrentUser Member member, HttpServletRequest request){
        return Result.success(crawlerProductService.findList(productIds));
    }


    private List<Map<String,Object>> tuiJianCategory(String name) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isNotBlank(name)){
            List<Term> terms = HanLP.segment(name);
            for (Term term:terms) {
                System.out.println(term.word.replace("\\/n",""));
                ProductCategory productCategory = productCategoryService.findByName(term.word.replace("\\/n",""));
                if(productCategory!=null){
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",productCategory.getId());
                    map.put("name",productCategory.getParents().stream().map(item->item.getName()).collect(Collectors.joining(","))+","+productCategory.getName());
                    list.add(map);
                }
            }
        }
        return list;
    }
}
