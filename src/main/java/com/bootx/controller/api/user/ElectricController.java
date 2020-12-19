package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.controller.api.user.demo.Data;
import com.bootx.controller.api.user.demo.JsonRootBean;
import com.bootx.entity.ElectricDiscount;
import com.bootx.service.ElectricDiscountService;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author black
 */
@RestController("apiUserElectricController")
@RequestMapping("/user/electric")
public class ElectricController extends BaseController {
    @Autowired
    private ElectricDiscountService electricDiscountService;

    @PostMapping("/discount")
    public Result discount(Long productId){
        return Result.success(Collections.emptyList());
    }

    @PostMapping("/page")
    public Result page(Integer excision,Integer page,Integer coinType){

        // data:{"type":500,"content":"没有更多数据了","date":null,"code":null,"message":null}
        return Result.ok(500, null,null,"没有更多数据了",null);
    }

    @PostMapping("/init")
    public Result init(Long productId){
        for (int i=300;i<600;i++) {
            String result = WebUtils.get("http://api.suanlifeng.com/user/electric/discount?productId="+i,null);
            JsonRootBean jsonRootBean = JsonUtils.toObject(result,JsonRootBean.class);
            if(jsonRootBean.getDate().size()>0){
                for (Data data:jsonRootBean.getDate()) {
                    ElectricDiscount electricDiscount = new ElectricDiscount();
                    electricDiscount.setDiscount(BigDecimal.valueOf(data.getDiscount()));
                    electricDiscount.setDay(data.getDay());
                    electricDiscount.setEnable(data.getEnable());
                    electricDiscount.setTag(data.getTag());
                    electricDiscount.setProductId(i+0L);
                    electricDiscount.setDeduction(BigDecimal.valueOf(data.getDeduction()));
                    electricDiscountService.save(electricDiscount);
                }
            }
        }

        return Result.success("ok");
    }

}
