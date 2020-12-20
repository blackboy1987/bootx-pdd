package com.bootx.controller.api.common;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("apiCommonIndexController")
@RequestMapping("/common")
public class IndexController extends BaseController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/judgePass")
    public Result judgePass(@CurrentUser Member member, Long userId, HttpServletRequest request,String password){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            member = memberService.find(userId);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        if(member.isValidCredentials1(password)){
            return Result.success("验证通过");
        }
        return Result.success("验证失败");

    }
}
