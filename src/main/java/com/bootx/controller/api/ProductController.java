package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.MineMachine;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MineMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("iconApiProductController")
@RequestMapping("/app/user/v2/product")
public class ProductController {

    @Autowired
    private BitCoinAccountService bitCoinAccountService;
    @Autowired
    private MineMachineService mineMachineService;


    @PostMapping("/specials_v3")
    public Result specialsV3(){
        List<MineMachine> listByCoinType = mineMachineService.findListByCoinType(5,1);
        return Result.success(listByCoinType.get(0));
    }

    @PostMapping("/list/coinType")
    public Result listCoinType(Integer size,Integer coinType){

        return Result.success(mineMachineService.findListByCoinType(coinType,size));
    }

    @PostMapping("/list")
    public Result list(Integer type,Integer coinType){
        type = 99;
        coinType=99;
        return Result.success(mineMachineService.findListByCoinTypeAndType(coinType,type));
    }

    @PostMapping("/detail")
    public Result detail(Long id){
        return Result.success(mineMachineService.find(id));
    }
}
