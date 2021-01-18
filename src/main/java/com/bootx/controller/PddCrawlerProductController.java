package com.bootx.controller;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.elasticsearch.service.EsPddCrawlerProductService;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.CrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.service.PddCrawlerProductService;
import com.bootx.pdd.service.PddLogService;
import com.bootx.security.CurrentUser;
import com.bootx.service.CrawlerProductService;
import com.bootx.service.ProductCategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author black
 */
@RestController("pddPddCrawlerProductController")
@RequestMapping("/pdd/product")
public class PddCrawlerProductController extends BaseController {

    @Resource
    private PddCrawlerProductService pddCrawlerProductService;

    @Resource
    private CrawlerProductService crawlerProductService;
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private PddLogService pddLogService;

    @Resource
    private EsPddCrawlerProductService esPddCrawlerProductService;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable, String name, String sn, Integer status, Date beginDate, Date endDate, @CurrentUser Member member){
        Page<PddCrawlerProduct> page = pddCrawlerProductService.findPage(pageable,name,sn,status,null,false,beginDate,endDate,member);
        for (PddCrawlerProduct product:page.getContent()) {
            product.setPddLogs(pddLogService.query(product));
        }
        return Result.success(page);
    }

    @PostMapping("/list1")
    @JsonView(BaseEntity.PageView.class)
    public Result list1(Pageable pageable, String name, String sn, Date beginDate, Date endDate, @CurrentUser Member member){ ;
        return Result.success(pddCrawlerProductService.findPage(pageable,name,sn,null,null,true,beginDate,endDate,member));
    }

    @PostMapping("changeTitle")
    public Message changeTitle(Long[] ids,String beforeWord,String afterWord,String oldWord, String newWord,String delWord, @CurrentUser Member member){
        List<PddCrawlerProduct> pddCrawlerProductList = pddCrawlerProductService.findList(ids);
        if(beforeWord==null){
            beforeWord="";
        }
        if(afterWord==null){
            afterWord="";
        }
        if(oldWord==null){
            oldWord="";
        }
        if(newWord==null){
            newWord="";
        }
        if(delWord==null){
            delWord="";
        }
        for (PddCrawlerProduct pddCrawlerProduct:pddCrawlerProductList) {
            String name = pddCrawlerProduct.getName();
            if(name==null){
                name="";
            }
            name = beforeWord+name;
            name = name+afterWord;
            name = name.replace(oldWord,newWord);
            name = name.replace(delWord,"");
            pddCrawlerProduct.setName(name);
            pddCrawlerProductService.update(pddCrawlerProduct);
        }
        return Message.success("操作成功");
    }

    @PostMapping("/delete")
    public Message delete(Long[] ids, @CurrentUser Member member){
        List<PddCrawlerProduct> pddCrawlerProductList = pddCrawlerProductService.findList(ids);
        for (PddCrawlerProduct pddCrawlerProduct:pddCrawlerProductList) {
            pddCrawlerProduct.setIsDeleted(true);
            pddCrawlerProductService.update(pddCrawlerProduct);
        }
        return Message.success("操作成功");
    }

    @PostMapping("/reset")
    public Message reset(Long[] ids, @CurrentUser Member member){
        List<PddCrawlerProduct> pddCrawlerProductList = pddCrawlerProductService.findList(ids);
        for (PddCrawlerProduct pddCrawlerProduct:pddCrawlerProductList) {
            pddCrawlerProduct.setIsDeleted(false);
            pddCrawlerProductService.update(pddCrawlerProduct);
        }
        return Message.success("操作成功");
    }

    @PostMapping("/crawler")
    public Message crawler(Long id, @CurrentUser Member member){
        PddCrawlerProduct pddCrawlerProduct = pddCrawlerProductService.find(id);
        if(pddCrawlerProduct!=null){
            List<CrawlerProduct> crawlerProducts = new ArrayList<>();
            CrawlerProduct crawlerProduct = pddCrawlerProduct.getCrawlerProduct();
            if(crawlerProduct!=null){
                crawlerProduct.setStatus(0);
                crawlerProduct.setPddCrawlerProduct(pddCrawlerProduct);
                crawlerProducts.add(crawlerProduct);
                crawlerProductService.crawler(crawlerProducts,member);

                esPddCrawlerProductService.add(pddCrawlerProduct);
            }

        }
        return Message.success("操作完成");
    }

    @PostMapping("/updateProductCategory")
    public Message updateProductCategory(Long id,Long[] productCategoryIds, @CurrentUser Member member){
        PddCrawlerProduct pddCrawlerProduct = pddCrawlerProductService.find(id);
        if(pddCrawlerProduct!=null){
            pddCrawlerProduct.setProductCategory(productCategoryService.find(productCategoryIds[productCategoryIds.length-1]));
            pddCrawlerProductService.update(pddCrawlerProduct);
        }
        return Message.success("操作完成");
    }

    @PostMapping("/publish")
    public Message publish(Long[] ids,Long[] storeIds, @CurrentUser Member member) throws Exception {

        pddCrawlerProductService.publish(ids,storeIds);


        return Message.success("操作完成");
    }
}
