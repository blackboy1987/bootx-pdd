package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CacheService;
import com.bootx.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("apiAppUserSafeController")
@RequestMapping("/app/user/safe")
public class SafeController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CacheService cacheService;

    /**
     * 重置资金密码
     * @param oldPass
     * @param newPass
     * @param sourPass
     * @param request
     * @param member
     * @return
     */
    @PostMapping("/recash")
    public Result recash(String oldPass, String newPass, String sourPass, HttpServletRequest request, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        if(member.getEncodedPassword1()!=null&&!member.isValidCredentials1(oldPass)){
            return Result.error("原资金密码错误，请重新输入");
        }
        if(!StringUtils.equalsIgnoreCase(newPass,sourPass)){
            return Result.error("两次密码输入不一致");
        }
        member.setPassword1(sourPass);
        memberService.update(member);
        return Result.success("修改成功");
    }

    /**
     * 忘记资金密码
     * @param oldPass
     * @param newPass
     * @param sourPass
     * @param request
     * @param member
     * @return
     */
    @PostMapping("/forget_pass")
    public Result forgetPass(String oldPass,String newPass,String sourPass,HttpServletRequest request,@CurrentUser Member member,String code){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        if(StringUtils.isNotBlank(code)){
            if(!cacheService.smsCodeCacheValidate(member.getMobile(),code)){
                return Result.error("验证码校验失败");
            }
        }else{
            if(!member.isValidCredentials1(oldPass)){
                return Result.error("原资金密码错误，请重新输入");
            }
        }
        if(StringUtils.equalsIgnoreCase(newPass,sourPass)){
            return Result.error("两次密码输入不一致");
        }
        member.setPassword1(sourPass);
        memberService.update(member);
        return Result.success("修改成功");
    }

    @PostMapping("/repass")
    public Result repass(String oldPass,String newPass,String sourPass,HttpServletRequest request,@CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        if(!member.isValidCredentials1(oldPass)){
            return Result.error("原登录密码错误，请重新输入");
        }
        if(StringUtils.equalsIgnoreCase(newPass,sourPass)){
            return Result.error("两次密码输入不一致");
        }
        member.setPassword(sourPass);
        memberService.update(member);
        return Result.success("修改成功");
    }
}
