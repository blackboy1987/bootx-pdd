package com.bootx.controller.es;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.elasticsearch.service.EsPddCrawlerProductService;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/pdd/es/product")
@CrossOrigin
public class EsPddCrawlerProductController {

    @Resource
    private EsPddCrawlerProductService esPddCrawlerProductService;

    @PostMapping("/list")
    public Result list(Pageable pageable, String name, String sn, Integer status,String batchId,Integer publishStatus, Date beginDate, Date endDate, @CurrentUser Member member) throws IOException {
        return Result.success(esPddCrawlerProductService.findPage(pageable,name,sn,status,batchId,publishStatus,false,beginDate,endDate,member));
    }

}