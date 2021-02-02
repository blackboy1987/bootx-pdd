package com.bootx.controller;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/pdd/member")
@CrossOrigin
public class MemberController extends BaseController {

    @PostMapping("/rule")
    public Result currentUser(@CurrentUser Member member, Pageable pageable){

        return Result.success(new Page<Object>(Collections.emptyList(),0L,pageable));
    }
}
