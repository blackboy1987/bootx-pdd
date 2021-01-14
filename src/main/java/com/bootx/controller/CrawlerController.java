package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CrawlerProductService;
import com.bootx.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author black
 */
@RestController("pddCrawlerController")
@RequestMapping("/pdd/crawler")
public class CrawlerController extends BaseController {

    @Resource
    private CrawlerProductService crawlerProductService;
    @Resource
    private MemberService memberService;

    @PostMapping
    public Result crawler(String[] urls, Integer type, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("请先登录");
        }
        crawlerProductService.crawler(member,urls,type);
        return Result.success("success");
    }




    @PostMapping("/detail")
    @JsonView(BaseEntity.EditView.class)
    public Result detail(Long[] productIds, @CurrentUser Member member, HttpServletRequest request){
        return Result.success(crawlerProductService.findList(productIds));
    }
}
