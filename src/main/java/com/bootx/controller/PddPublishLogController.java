package com.bootx.controller;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.elasticsearch.entity.EsPddPublishLog;
import com.bootx.elasticsearch.service.EsPddPublishLogService;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.StoreService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @author black
 */
@RestController("pddPddPublishLogController")
@RequestMapping("/pdd/publish_log")
@CrossOrigin
public class PddPublishLogController extends BaseController {

    @Resource
    private EsPddPublishLogService esPddPublishLogService;
    @Resource
    private StoreService storeService;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable, String name, Integer status, Date beginDate, Date endDate,Long storeId, @CurrentUser Member member) throws IOException {
        Page<EsPddPublishLog> page = esPddPublishLogService.findPage(pageable, name, status, beginDate, endDate, storeService.find(storeId));
        return Result.success(page);
    }
}
