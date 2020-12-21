package com.bootx.controller.api.app.user.v2;

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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/user/v2/auth")
public class AuthController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/info")
    public Result info(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        if(member.getIsAuth()){
            Map<String,Object> data= new HashMap<>();
            data.put("state",2);
            return Result.success(data);
        }
        return Result.success(null);
    }

}
