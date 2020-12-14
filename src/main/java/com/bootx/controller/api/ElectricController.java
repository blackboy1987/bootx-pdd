package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userElectricController")
@RequestMapping("/user/electric")
public class ElectricController {

    @Autowired
    private BitCoinAccountService bitCoinAccountService;
    @Autowired
    private MemberService memberService;

   @PostMapping("/page")
    public Result page(Integer excision,Integer page,Integer coinType){

       // data:{"type":500,"content":"没有更多数据了","date":null,"code":null,"message":null}
       return Result.ok(500, null,null,"没有更多数据了",null);
    }
    @PostMapping("discount")
    public Result discount(Long productId){

        // data:{"type":500,"content":"没有更多数据了","date":null,"code":null,"message":null}
        return Result.ok(500, null,null,"没有更多数据了",null);
    }
}
