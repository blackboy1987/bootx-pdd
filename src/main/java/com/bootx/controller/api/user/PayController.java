package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author black
 */
@RestController("apiUserPayController")
@RequestMapping("/app/user/pay")
public class PayController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BitCoinAccountService bitCoinAccountService;

    @PostMapping("/request")
    public Result request(BigDecimal money, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        if(money!=null&&money.compareTo(BigDecimal.ZERO)>0){
            member.setBalance(member.getBalance().add(money));
            memberService.update(member);
            BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 3);
            if(bitCoinAccount!=null&&!bitCoinAccount.isNew()){
                bitCoinAccount.setMoney(bitCoinAccount.getMoney().add(money));
                bitCoinAccountService.update(bitCoinAccount);
            }

            return Result.success("充值成功,等待系统审核");
        }else{
            return Result.error("充值金额有误");
        }




    }

}
