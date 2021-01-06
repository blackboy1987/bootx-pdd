package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author black
 */
@RestController("pddProductController")
@RequestMapping("/pdd/product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    @GetMapping("/crawler")
    public String crawler(String[] urls, Integer type, @CurrentUser Member member){
        productService.crawler(urls,type);
        return "ok";
    }

}
