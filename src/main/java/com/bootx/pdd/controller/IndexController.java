package com.bootx.pdd.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.pdd.service.PddService;
import com.bootx.security.UserAuthenticationToken;
import com.bootx.service.MemberService;
import com.bootx.service.StoreService;
import com.bootx.service.UserService;
import com.bootx.util.JWTUtils;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    /**
     *
     * @param code
     *      用来获取accessToken的code码
     * @param state
     *      用户的token
     * @return
     * @throws Exception
     */
    @GetMapping
    public AccessTokenResponse index(String code,String state) throws Exception {
        AccessTokenResponse accessTokenResponse = pddService.token(code);
        if(accessTokenResponse!=null){
            Member member = storeService.create(accessTokenResponse,memberService.getCurrent(state)).getMember();
            if(member!=null){
                userService.login(new UserAuthenticationToken(Member.class, member.getUsername(), "12345678", false, "0:0:0:0"));
            }
        }
        return accessTokenResponse;
    }

    @PostMapping("/authLogin")
    public Map<String,Object> authLogin(String code, String state) throws Exception {
        Map<String,Object> data = new HashMap<>();
        AccessTokenResponse accessTokenResponse = pddService.token(code);
        if(accessTokenResponse.getErrorResponse()==null){
            data.put("code",0);
            Member member = null;
            // 获取成功
            if(StringUtils.isBlank(state)){
                member = storeService.create(accessTokenResponse,memberService.getCurrent(state)).getMember();
            }else{
                String [] states = state.split("::");
                if(states.length==1){
                    member = storeService.create(accessTokenResponse,memberService.getCurrent(state)).getMember();
                }else if(states.length==2){
                    member = storeService.create1(accessTokenResponse,memberService.getCurrent1(states[0])).getMember();
                }
            }
            Map<String,Object> user = new HashMap<>();
            user.put("id",member.getId());
            user.put("username",member.getUsername());
            data.put("token", JWTUtils.create(member.getId()+"",user));
        }else{
            data.put("code",-1);
            data.put("msg", accessTokenResponse.getErrorResponse().getErrorMsg());
        }
        return data;
    }

}
