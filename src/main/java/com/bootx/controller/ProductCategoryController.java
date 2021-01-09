package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.ProductCategory;
import com.bootx.service.ProductCategoryService;
import com.fasterxml.jackson.annotation.JsonView;
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
public class ProductCategoryController extends BaseController {

    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/tree")
    @JsonView(BaseEntity.TreeView.class)
    public List<ProductCategory> tree(){
        return productCategoryService.findRoots("pddPlugin");
    }

}
