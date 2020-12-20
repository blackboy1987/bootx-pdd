package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController("apiUserMoneyController")
@RequestMapping("/app/user/money")
public class MoneyController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BitCoinAccountService bitCoinAccountService;
    @Autowired
    private BitCoinAccountBankService bitCoinAccountBankService;
    @Autowired
    private BitCoinAccountMoneyService bitCoinAccountMoneyService;
    @Autowired
    private BitCoinAccountWalletService bitCoinAccountWalletService;
    @Autowired
    private BitCoinAccountRuleService bitCoinAccountRuleService;

    @PostMapping("/details")
    public Result details(Integer asset,@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(),asset);

        Map<String,Object> data = new HashMap<>();
        data.put("coinNum",1);
        data.put("withdraw",0);
        data.put("bank",bitCoinAccountBankService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(),member.getId()));
        data.put("wallet",bitCoinAccountWalletService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(),member.getId()));
        data.put("money",bitCoinAccountMoneyService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(),member.getId()));
        data.put("rule",bitCoinAccountRuleService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(),member.getId()));
        return Result.success(data);
    }

    @RequestMapping("/list")
    public Result moneyList(@CurrentUser Member member, HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        data.put("totalEarnings",127.29);
        data.put("totalMoney",127.29);
        data.put("list",bitCoinAccountService.findByUserId(member.getId()));
        return Result.success(data);
    }
}
