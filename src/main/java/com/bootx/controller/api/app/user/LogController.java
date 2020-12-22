package com.bootx.controller.api.app.user;

import com.bootx.common.Page;
import com.bootx.common.Page1;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.AccountLog;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AccountLogService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("appUserLogController")
@RequestMapping("/app/user/log")
public class LogController extends BaseController {

    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private MemberService memberService;

    /**
     * 交易明细
     * @param member
     * @param page
     * @param limit
     * @param type
     *      0：全部
     *      1：收入
     *      1：支出
     * @param request
     * @return
     */
    @PostMapping("/rmbList")
    public Result rmbList(@CurrentUser Member member, Integer page, Integer limit, Integer type, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        Pageable pageable = new Pageable(page,limit);
        Page<AccountLog> page1 = accountLogService.findPage(pageable,member.getId(),type);
        return Result.success(new Page1<AccountLog>(page1.getContent(), page1.getTotal(), pageable));
    }

}
