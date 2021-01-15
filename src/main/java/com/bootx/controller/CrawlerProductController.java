package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CrawlerProductService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author black
 */
@RestController("pddCrawlerProductController")
@RequestMapping("/pdd/crawler_product")
public class CrawlerProductController extends BaseController {

    @Resource
    private CrawlerProductService crawlerProductService;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable, @CurrentUser Member member){ ;
        return Result.success(crawlerProductService.findPage(pageable));
    }
}
