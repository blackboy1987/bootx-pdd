package com.bootx.controller;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.StoreCategory;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.StoreCategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author black
 */
@RestController("pddStoreCategoryController")
@RequestMapping("/pdd/store_category")
@CrossOrigin
public class StoreCategoryController extends BaseController {

    @Resource
    private StoreCategoryService storeCategoryService;
    @Resource
    private MemberService memberService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Message list(Pageable pageable, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        return Message.success(storeCategoryService.findPage(pageable,member));
    }

    @PostMapping("/save")
    public Message save(StoreCategory storeCategory, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Message.error("登录信息已过期,请重新登录");
        }
        if(storeCategory.getIDefault()==null){
            storeCategory.setIsDefault(false);
        }
        storeCategory.setMember(member);
        storeCategoryService.save(storeCategory);
        return Message.success("保存成功");
    }

    @PostMapping("/update")
    public Message update(StoreCategory storeCategory, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Message.error("登录信息已过期,请重新登录");
        }
        if(storeCategory.getIDefault()==null){
            storeCategory.setIsDefault(false);
        }
        storeCategory.setMember(member);
        storeCategoryService.update(storeCategory);
        return Message.success("保存成功");
    }
}
