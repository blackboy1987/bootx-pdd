package com.bootx.controller.api.common;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.service.CacheService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiCommonVerifierControll")
@RequestMapping("/common/verifier")
public class VerifierControler extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CacheService cacheService;

    @PostMapping("/phone/code")
    public Result verifierPhoneCode(String phone, String phonecode){
        Member member = memberService.findByMobile(phone);
        if(member==null){
            return Result.error("该手机号暂未注册！");
        }
        if(!cacheService.smsCodeCacheValidate(phone,phonecode)){
            return Result.error("短信验证码错误或已失效，请重新输入");
        }
        return Result.success("");
    }

}
