package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.service.MemberService;
import com.bootx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("iconApiLogoutController")
@RequestMapping("/app/auth/out")
public class LogoutController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;


    /**
     * 退出
     * @return
     */
    @PostMapping
    public Result index (){
        userService.logout();
        return Result.success("");
    }
}
