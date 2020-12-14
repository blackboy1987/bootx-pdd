package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("iconApiUserController")
@RequestMapping("/app/user")
public class UserController {

    @Autowired
    private BitCoinAccountService bitCoinAccountService;
    @Autowired
    private MemberService memberService;

    /**
     * data,{"type",200,"content","","date",{"totalEarnings","0.00","totalMoney","0.00","list",[{"id",1763110,"userId",218776,"assetType",1,"money",0,"frozenMoney",0,"state",true,"name","BTC","price","0.00"},{"id",1763111,"userId",218776,"assetType",2,"money",0,"frozenMoney",0,"state",true,"name","USDT","price","0.00"},{"id",1763112,"userId",218776,"assetType",3,"money",0,"frozenMoney",0,"state",true,"name","CNY","price","0.00"},{"id",1763113,"userId",218776,"assetType",4,"money",0,"frozenMoney",0,"state",true,"name","HBT","price","0.00"},{"id",1763114,"userId",218776,"assetType",5,"money",0,"frozenMoney",0,"state",true,"name","ETH","price","0.00"}]},"code",null,"message",null}
     *
     */
    @RequestMapping("/money/list")
    public Result moneyList(@CurrentUser Member member, HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();
        if(member==null){
            member = memberService.getCurrent(request);
        }
        data.put("totalEarnings",0.0);
        data.put("totalMoney",0.00);
        System.out.println(member);
        bitCoinAccountService.initAccount(member);
        data.put("list",bitCoinAccountService.findByUserId(member.getId()));
        return Result.success(data);
    }

    @PostMapping("/info/index")
    public Result infoIndex(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",member.getId());
        data.put("userName",member.getUsername());
        data.put("icon","/templates/images/head/010.png");
        data.put("phone",member.getMobile());
        data.put("email",member.getEmail());
        data.put("digest",null);
        data.put("password",null);
        data.put("tradePwd",null);
        data.put( "isActivation",false);
        data.put( "isLock",member.getIsLocked());
        data.put( "isEnable",member.getIsEnabled());
        data.put("extendCode","73161116");
        data.put( "child",12);
        data.put( "isAuth",false);
        data.put( "createDate",member.getCreatedDate());
        data.put("modifyDate",member.getLastModifiedDate());
        data.put("centerOpt",135);
        data.put( "centerMeb",228);
        data.put( "member",45);
        data.put( "memberChild",14);
        data.put( "bindShop",313);
        data.put( "bindTime",null);
        data.put( "umeng",null);
        data.put( "ip","220.189.220.65, 59.56.79.17");
        data.put("status",1);
        data.put( "internal",false);
        data.put( "online",false);
        data.put( "lastLoginDate",member.getLastLoginDate());
        data.put( "investNum",null);
        data.put( "parentName",null);
        data.put( "lastIp",member.getLastLoginIp());
        data.put( "mobile",member.getMobile());
        data.put(  "addPass",true);
        data.put(  "userWallet",null);
        data.put(  "userMoney",null);
        data.put( "shopName",null);
        data.put( "shopType",null);
        data.put( "typeName",null);
        
        
        

        return Result.success(data);
    }

    @PostMapping("/profit/list")
    public Result profitIndex(@CurrentUser Member member,Integer coinType, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",member.getId());
        data.put("userName",member.getUsername());
        data.put("icon","/templates/images/head/010.png");
        data.put("phone",member.getMobile());
        data.put("email",member.getEmail());
        data.put("digest",null);
        data.put("password",null);
        data.put("tradePwd",null);
        data.put( "isActivation",false);
        data.put( "isLock",member.getIsLocked());
        data.put( "isEnable",member.getIsEnabled());
        data.put("extendCode","73161116");
        data.put( "child",12);
        data.put( "isAuth",false);
        data.put( "createDate",member.getCreatedDate());
        data.put("modifyDate",member.getLastModifiedDate());
        data.put("centerOpt",135);
        data.put( "centerMeb",228);
        data.put( "member",45);
        data.put( "memberChild",14);
        data.put( "bindShop",313);
        data.put( "bindTime",null);
        data.put( "umeng",null);
        data.put( "ip","220.189.220.65, 59.56.79.17");
        data.put("status",1);
        data.put( "internal",false);
        data.put( "online",false);
        data.put( "lastLoginDate",member.getLastLoginDate());
        data.put( "investNum",null);
        data.put( "parentName",null);
        data.put( "lastIp",member.getLastLoginIp());
        data.put( "mobile",member.getMobile());
        data.put(  "addPass",true);
        data.put(  "userWallet",null);
        data.put(  "userMoney",null);
        data.put( "shopName",null);
        data.put( "shopType",null);
        data.put( "typeName",null);




        return Result.success(data);
    }



    @PostMapping("/v2/auth/info")
    public Result authInfo(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("请先登录");
        }

        return Result.success("");
    }

    @PostMapping("/v2/order/rule")
    public Result a(){

        // data:{"type":200,"content":"","date":{"id":1,"largess":5,"largessBtc":true,"largessFil":false,"largessHpt":true,"btcPrice":19213,"hptPrice":0.1,"ethPrice":594.35,"filPrice":29.84,"investRate":0,"exchangeRate":6.63,"hbtExchangeRate":10,"enableEthElectricBuy":false,"popusStatus":true,"ipfsActivityStatus":true,"filBlockStatus":true},"code":null,"message":null}
        return Result.success("");


    }

    @PostMapping("/v2/order/myPage")
    public Result orderMyPage(Integer excision,Integer page,Integer type,String orderType,Integer coinType){

        // data:{"type":500,"content":"没有更多数据了","date":null,"code":null,"message":null}
        return Result.success("");
    }
}
