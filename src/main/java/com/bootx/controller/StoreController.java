package com.bootx.controller;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.Store;
import com.bootx.entity.StoreUploadConfig;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.StoreService;
import com.fasterxml.jackson.annotation.JsonView;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("pddStoreController")
@RequestMapping("/pdd/store")
@CrossOrigin
public class StoreController extends BaseController {

    @Resource
    private StoreService storeService;
    @Resource
    private MemberService memberService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Message list(@CurrentUser Member member, HttpServletRequest request, Pageable pageable){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        return Message.success(storeService.findPage(pageable,member));
    }

    @PostMapping("/unbind")
    public Message unbind(@CurrentUser Member member, HttpServletRequest request, Long id){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Message.error("登录信息已过期，请重新登录");
        }
        Store store = storeService.find(id);
        if(store==null||store.getMember()!=member){
            return Message.error("不存在该店铺");
        }

        return storeService.unbind(store);

    }


    @PostMapping("/tree")
    public List<Map<String,Object>> tree(@CurrentUser Member member){
        return storeService.tree(member);
    }

    @PostMapping("/info")
    public PddMallInfoGetResponse info(Long storeId) throws Exception {
        return storeService.info(storeId);
    }

    @PostMapping("/config")
    public Result config(Long storeId) {
        if(storeId==null){
            return Result.error("请先选择一个店铺");
        }
        Store store = storeService.find(storeId);
        if(store==null){
            return Result.error("店铺不存在");
        }
        StoreUploadConfig storeUploadConfig = store.getStoreUploadConfig();
        Map<String,Object> data = new HashMap<>();
        data.put("storeUploadConfig", storeService.build(storeUploadConfig));
        data.put("storeDeliveryTemplates",store.getStoreDeliveryTemplates());
        return Result.success(data);
    }

    @PostMapping("/deliveryTemplate")
    public Result deliveryTemplate(Long storeId) throws Exception {
        return Result.success(storeService.updateDeliveryTemplate(storeId));
    }

    @PostMapping("/updateConfig")
    public Result updateConfig(Long storeId,StoreUploadConfig storeUploadConfig) throws Exception {
        if(storeId==null){
            return Result.error("请先选择一个店铺");
        }
        Store store = storeService.find(storeId);
        if(store==null){
            return Result.error("店铺不存在");
        }
        store.setStoreUploadConfig(storeService.build(storeUploadConfig));
        storeService.update(store);
        return Result.success("ok");
    }

}
