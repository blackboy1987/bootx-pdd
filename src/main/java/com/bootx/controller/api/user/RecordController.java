package com.bootx.controller.api.user;

import com.bootx.common.Page;
import com.bootx.common.Page1;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.AccountCoinLog;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AccountCoinLogService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("apiUserRecordController")
@RequestMapping("/app/user/record")
public class RecordController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountCoinLogService accountCoinLogService;

    /**
     * 提现
     * @param member
     * @param userId
     * @param amount
     * @param bankId
     * @param request
     * @return
     */
    @PostMapping("/page")
    public Result page(@CurrentUser Member member, Integer excision, Integer asset, String account,Integer page, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        Pageable pageable = new Pageable(page,5);
        Page<AccountCoinLog> page1 = accountCoinLogService.findPage(pageable,member.getId(),excision,asset,account,null);
        return Result.success(new Page1(page1.getContent(), page1.getTotal(), pageable));
    }

}
