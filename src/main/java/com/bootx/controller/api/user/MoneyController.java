package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
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

    @PostMapping("/details")
    public Result details(Integer asset){

        return Result.success("");
    }

    @RequestMapping("/list")
    public Result moneyList(@CurrentUser Member member, HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();
        if(member==null){
            member = memberService.getCurrent(request);
        }
        data.put("totalEarnings",127.29);
        data.put("totalMoney",127.29);
        data.put("list",bitCoinAccountService.findByUserId(member.getId()));
        return Result.success(data);
    }
}
