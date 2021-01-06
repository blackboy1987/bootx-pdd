
package com.bootx.service.impl;

import com.bootx.entity.CrawlerLog;
import com.bootx.entity.Member;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.MemberService;
import com.bootx.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CrawlerLogServiceImpl extends BaseServiceImpl<CrawlerLog, Long> implements CrawlerLogService {

    @Resource
    private MemberService memberService;
    @Resource
    private UserService userService;

    @Override
    @Async
    public void save(String[] urls, Integer type) {
        Member member = userService.getCurrent(Member.class);
    }
}