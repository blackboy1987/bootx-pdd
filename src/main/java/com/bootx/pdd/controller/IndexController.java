package com.bootx.pdd.controller;

import com.bootx.constants.PddConfig;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.pdd.service.PddService;
import com.bootx.security.UserAuthenticationToken;
import com.bootx.service.MemberService;
import com.bootx.service.StoreService;
import com.bootx.service.UserService;
import com.bootx.util.ImageUtils;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsImageUploadResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddMallInfoGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author black
 */
@RestController("pddIndexController")
@RequestMapping("/pdd")
public class IndexController extends BaseController {

    @Autowired
    private PddService pddService;

    @Resource
    private StoreService storeService;

    @Resource
    private MemberService memberService;
    @Resource
    private UserService userService;

    @GetMapping
    public AccessTokenResponse index(String code) throws Exception {
        AccessTokenResponse accessTokenResponse = pddService.token(code);
        if(accessTokenResponse!=null){
            Member member = storeService.create(accessTokenResponse).getMember();
            if(member!=null){
                userService.login(new UserAuthenticationToken(Member.class, member.getUsername(), "12345678", false, "0:0:0:0"));
            }
        }
        return accessTokenResponse;
    }

    @GetMapping("/upload")
    public PddGoodsImageUploadResponse upload(String url) throws Exception {
        return pddService.uploadImage("data:image/png;base64,"+ImageUtils.url2Base64(url), PddConfig.accessToken);
    }

    @GetMapping("/store_info")
    public PddMallInfoGetResponse storeInfo(String accessToken) throws Exception {
        return pddService.storeInfo(accessToken);
    }
}
