package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.Store;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.StoreService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author black
 */
@RestController("pddStoreController")
@RequestMapping("/pdd/store")
public class StoreController extends BaseController {

    @Resource
    private StoreService storeService;
    @Resource
    private MemberService memberService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public List<Store> list(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        List<Store> stores = storeService.findList(member);
        // 通过刷新accessToken的方式来判断店铺登录信息
        for (Store store:stores){
            storeService.flushAccessToken(store);
        }

        return storeService.findList(member);
    }
}
