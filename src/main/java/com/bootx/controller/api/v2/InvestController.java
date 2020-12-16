package com.bootx.controller.api.v2;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("userV2InvestController")
@RequestMapping("/app/user/v2/invest")
public class InvestController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/page/byDate")
    public Result pageByDate(@CurrentUser Member member, HttpServletRequest request, Integer conitType, Integer limit,Integer page){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("请先登录");
        }
        return Result.success("");
    }

}
