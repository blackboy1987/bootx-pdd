package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author black
 */
@RestController("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {

    @Resource
    private MemberService memberService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;

    @PostMapping("/page")
    @JsonView(BaseEntity.PageView.class)
    public Message page(Pageable pageable, String username, String name, Date beginDate, Date endDate){
        Page<Member> page = memberService.findPage(pageable,username,name,beginDate,endDate);
        for (Member member: page.getContent()) {
            member.setBitCoinAccounts(bitCoinAccountService.findByUserId(member.getId()));
        }
        return Message.success(page);
    }

}
