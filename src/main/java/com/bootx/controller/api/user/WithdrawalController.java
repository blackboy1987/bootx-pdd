package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController("apiUserWithdrawalController")
@RequestMapping("/app/userWithdrawal")
public class WithdrawalController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/getBankCard")
    public Result getBankCard(@CurrentUser Member member,Long userId, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            member = memberService.find(userId);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        Map<String,Object> data = new HashMap<>();
        if(StringUtils.isBlank(member.getBankArea())||StringUtils.isBlank(member.getBankCard())||StringUtils.isBlank(member.getBankCard())){
            return Result.success(null);
        }
        data.put("area",member.getBankArea());
        data.put("theirBank",member.getBankName());
        data.put("bankCard",member.getBankCard());
        data.put("money",1234);
        return Result.success(data);
    }

    /**
     * 提现
     * @param member
     * @param userId
     * @param amount
     * @param bankId
     * @param request
     * @return
     */
    @PostMapping("/requestWithdrawal")
    public Result requestWithdrawal(@CurrentUser Member member, Long userId, BigDecimal amount,Long bankId, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            member = memberService.find(userId);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        Map<String,Object> data = new HashMap<>();
        if(member.getBalance().compareTo(amount)<0){
            return Result.error("可提现余额不足");
        }
        data.put("type",0);
        data.put("bankCard",member.getBankCard());
        data.put("money",amount);
        return Result.success(data);
    }

}
