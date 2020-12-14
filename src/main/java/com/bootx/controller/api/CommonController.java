package com.bootx.controller.api;


import com.bootx.common.Result;
import com.bootx.entity.Member;
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
        String result = SmsUtils.send(tel,"【xxx】 您正在注册*** ，本次验证码为："+code+"。10分钟之内有效！");
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
        String result = SmsUtils.send(tel,"【xxx】 您正进行重置密码操作 ，本次验证码为："+code+"。10分钟之内有效！");
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

        Map<String,Object> result = new HashMap<>();
        result.put("accessid","accessid");
        result.put("policy","policy");
        result.put("signature","signature");
        result.put("dir","dir");
        result.put("host","host");
        result.put("expire","expire");
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
}
