package com.bootx.controller.api.app;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.service.CacheService;
import com.bootx.service.MemberService;
import com.bootx.service.UserBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("appUserBindController")
@RequestMapping("/app/userBind/")
public class UserBindController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserBindService userBindService;

    @PostMapping("/add")
    public Result add(String code,String phone,String uuid,String mobile){
        Member member = memberService.findByMobile(phone);
        if(member==null){
            Result.error("手机号未注册");
        }
        if(!cacheService.smsCodeCacheValidate(phone,code)){
            Result.error("验证码校验失败");
        }
        userBindService.create(member,uuid,mobile);
        return Result.success("");
    }
}
