package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("iconUserController")
@RequestMapping("/user")
public class User1Controller {

    @RequestMapping("/electric/page")
    public Result moneyList(@CurrentUser Member member, HttpServletRequest request){
        return Result.success("没有更多数据了");
    }
}
