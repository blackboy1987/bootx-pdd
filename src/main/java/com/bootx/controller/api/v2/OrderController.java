package com.bootx.controller.api.v2;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachineOrder;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.MineMachineOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("1")
@RequestMapping("/app/user/v2/order")
public class OrderController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MineMachineOrderService mineMachineOrderService;


    @PostMapping("/page")
    public Result orderPage(HttpServletRequest request, @CurrentUser Member member, Long parsentUserId, Integer excision,Integer page){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        Member current = memberService.find(parsentUserId);
        if(current==null|| current.getParent()!=member){
            return Result.error("信息错误");
        }
        Map<String,Object> data = new HashMap<>();
        Pageable pageable = new Pageable(page,10);
        Page<MineMachineOrder> page1 = mineMachineOrderService.findPage(pageable);
        data.put("list",page1.getContent());
        data.put("page",page1.getPageNumber());
        data.put("excision",excision);
        return Result.success(data);
    }
}
