package com.bootx.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("adminLoginController")
@RequestMapping("/admin/login")
public class LoginController extends BaseController {

    @PostMapping
    public Map<String,Object> index(){
        Map<String,Object> data=  new HashMap<>();
        data.put("currentAuthority","admin");
        data.put("status","ok");
        data.put("type","account");
        return data;
    }

    private Map<String,Object> getUser() {
        /**
         * avatar?: string;
         *     name?: string;
         *     title?: string;
         *     group?: string;
         *     signature?: string;
         *     tags?: {
         *       key: string;
         *       label: string;
         *     }[];
         *     userid?: string;
         *     access?: 'user' | 'guest' | 'admin';
         *     unreadCount?: number;
         */
        Map<String,Object> data=  new HashMap<>();
        data.put("name","name");
        data.put("title","title");
        data.put("group","group");
        data.put("signature","signature");
        data.put("userid","123");
        data.put("access","admin");
        data.put("unreadCount",0);
        return data;
    }
}
