package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.ProductImage;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.ProductImageService;
import com.bootx.util.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/pdd/common")
@CrossOrigin
public class CommonController extends BaseController {

    @Resource
    private MemberService memberService;
    @Resource
    private ProductImageService productImageService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file, @CurrentUser Member member, HttpServletRequest request,String token){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            member = memberService.getCurrent(token);
        }

        String date= DateUtils.formatDateToString(new Date(),"yyy_MM_dd");
        String uploadPath = "tmp/"+ (member!=null?member.getUsername():"unknown") +"_" +date+"_";
        ProductImage productImage = productImageService.generate(file, uploadPath);
        return Result.success(productImage.getLarge());
    }
}
