package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.MemberService;
import com.bootx.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author black
 */
@RestController("pddProductController")
@RequestMapping("/pdd/product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;
    @Resource
    private CrawlerLogService crawlerLogService;
    @Resource
    private MemberService memberService;

    @PostMapping("/crawler")
    public Result crawler(String[] urls, Integer type, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        productService.crawler(crawlerLogService.save(member,urls, type),urls,type);
        return Result.success("success");
    }

}
