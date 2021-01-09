package com.bootx.controller;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.CrawlerUrlLog;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CrawlerUrlLogService;
import com.bootx.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author black
 */
@RestController("pddCrawlerUrlLogController")
@RequestMapping("/pdd/crawler_url_log")
public class CrawlerUrlLogController extends BaseController {

    @Resource
    private CrawlerUrlLogService crawlerUrlLogService;
    @Resource
    private ProductService productService;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable, @CurrentUser Member member){
        Page<CrawlerUrlLog> page = crawlerUrlLogService.findPage(pageable,member,1);
        for (CrawlerUrlLog crawlerUrlLog:page.getContent()) {
            crawlerUrlLog.setProduct(productService.find(crawlerUrlLog.getProductId()));
        }
        return Result.success(page);
    }


    @PostMapping("/upload")
    @JsonView(BaseEntity.PageView.class)
    public Result upload(Long id, @CurrentUser Member member) throws Exception {
        crawlerUrlLogService.upload(id);

        return Result.success("ok");
    }
}
