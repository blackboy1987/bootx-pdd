package com.bootx.controller.api;


import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.bootx.StsServiceSample;
import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AdversiteService;
import com.bootx.service.MemberService;
import com.bootx.util.CodeUtils;
import com.bootx.util.SmsUtils;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("iconApiCommonController")
@RequestMapping("/app/common")
public class CommonController {

    @Autowired
    private AdversiteService adversiteService;
    /**
     * CacheManager
     */
    private static final Ehcache cache = CacheManager.create().getCache("smsCode");

    @Autowired
    private MemberService memberService;

    @RequestMapping("/news-readNum")
    public Result newsReadNum(Long userId){
        System.out.println("news-readNum:"+userId);
        return Result.success(0);
    }
    @RequestMapping("/adversite")
    public Result adversite(){
        return Result.success(adversiteService.findAll());
    }

    @PostMapping("/code/phone/add_user")
    public Result phoneAddUser(String tel){
        boolean b = memberService.mobileExists(tel);
        if(b){
            return Result.error("手机号码已注册，获取验证码失败");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(tel,"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){

            // 发送成功写入到缓存
            cache.put(new Element(tel,code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }


    @PostMapping("/code/phone/forget_pass")
    public Result phoneForgetPass(String tel){
        Member member = memberService.findByMobile(tel);
        if(member==null){
            return Result.error("该手机号暂未注册！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(tel,"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(tel,code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }

    @PostMapping("/online")
    public Result online(Long userId, Boolean online, HttpServletRequest request){
       Member member = memberService.getCurrent(request);
       if(member==null){
           member = memberService.find(userId);
       }
       if(member==null){
           return Result.error("请先登录");
       }

        return Result.success("离线");
    }

    @PostMapping("/oss/sts/token")
    public Result ossStsToken(){
        // data:{"type":200,"content":"","date":
        // {"accessid":"LTAIlRXU0scvCf3d",
        // "policy":"eyJleHBpcmF0aW9uIjoiMjAyMC0xMi0xNVQwODo1NzoyNy43OTlaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCJ1cGxvYWQvYXV0aC8yMDIwMTIxNS8iXV19",
        // "signature":"D1OTzU+zBchntkVxM9e/ByveZ9I=",
        // "dir":"upload/auth/20201215/",
        // "host":"https://hashbox.oss-cn-hangzhou.aliyuncs.com",
        // "expire":1608022647
        // },"code":null,"message":null}
        /**
         * Expiration: 2020-12-15T09:00:51Z
         * Access Key Id: STS.NU7hk2RxpLni2fu8mxbwS4u7t
         * Access Key Secret: 2abax6Crat8PT6QSfGCGrVJShsWEFupSrDH1VfbRNpio
         * Security Token: CAIShwJ1q6Ft5B2yfSjIr5aCI9GGv6dR+6yCMEDE3G0tbvh/27Ccljz2IHxIfHNrBe0YvvQwlG5T6vwflqdBS559TE3vScxt4q9L6lsjimQENYrng4YfgbiJREKhaXeirvKwDsz9SNTCAIDPD3nPii50x5bjaDymRCbLGJaViJlhHLt1Mw6jdmgEfpQ0QDFvs8gHL3DcGO+wOxrx+ArqAVFvpxB3hBEYi8G2ydbO7QHF3h+oiL1XhfyoesH+NZI1bMwgCI7vgLwmTMebjn4MsSot3bxtkalJ9Q3AutygGFRL632ESbGIroU1d1MkOPhqRP4a96SmyOcatO3XmpnvzxFEMfHV8JMPxXsuqRqAAYyhjvSu5fFgmFpoma7uUKt78dxNFfmR7IsGEu+5skic+EbOL9/g6FSdALeJSE5w3AHAf91NMwiBKZIKcWG4oCGUZMqbvb69rp+EIqzBXEiehK8jqxYNw4uk802buMydcdwiLAOuE5IaHZ+28yfxL01VBfVtfWn2qPln7+pPTclD
         * RequestId: 71A86A7F-7A5E-415E-AD04-6487EF03243A
         */

        AssumeRoleResponse.Credentials credentials = StsServiceSample.getStsToken();
        if(credentials==null){
            return Result.error("认证失败");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("accessid",credentials.getAccessKeyId());
        result.put("policy","policy");
        result.put("signature",credentials.getSecurityToken());
        result.put("dir","dir");
        result.put("host","host");
        result.put("expire",credentials.getExpiration());
        return Result.success(result);
    }

    @PostMapping("/payData")
    public Result payData(Long productId){
        // data:{"type":200,"content":"列表","date":
        // [{"id":4,"payId":4,"productId":null,"open":null,"name":"USDT","type":1,"remark":"USDT","zh_name":null},
        // {"id":5,"payId":5,"productId":null,"open":null,"name":"CNY","type":10,"remark":"CNY","zh_name":null},
        // {"id":6,"payId":6,"productId":null,"open":null,"name":"alipay","type":5,"remark":"支付宝","zh_name":null}
        // ],"code":null,"message":null}
        return Result.success("");
    }


    /**
     * 忘记资金密码的短信发送
     * @param tel
     * @return
     */
    @PostMapping("/code/phone/forget_money_pass")
    public Result forgetMoneyPass(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(member.getMobile(),"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(member.getMobile(),code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }

    /**
     * 更换绑定的手机号
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/code/phone/up_phone")
    public Result upPhone(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(member.getMobile(),"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(member.getMobile(),code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }

    /**
     * 更换绑定的手机号
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/obtain/phone/code")
    public Result phoneCode(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(member.getMobile(),"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(member.getMobile(),code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }


}
