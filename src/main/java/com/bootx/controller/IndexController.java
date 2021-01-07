package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdd")
public class IndexController extends BaseController {

    @Resource
    private MemberService memberService;

    @GetMapping("/currentUser")
    public Map<String,Object> currentUser(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id", member.getId());
        data.put("username", member.getUsername());
        return data;
    }
}
