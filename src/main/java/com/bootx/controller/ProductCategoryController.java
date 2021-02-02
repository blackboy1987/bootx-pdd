package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.elasticsearch.entity.ProductCategoryTree;
import com.bootx.service.ProductCategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author black
 */
@RestController("pddProductCategoryController")
@RequestMapping("/pdd/product_category")
@CrossOrigin
public class ProductCategoryController extends BaseController {

    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/tree")
    public List<ProductCategoryTree> tree(String pluginId){
        return productCategoryService.findTree1(pluginId);
    }

}
