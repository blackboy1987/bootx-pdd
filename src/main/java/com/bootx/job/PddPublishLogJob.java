package com.bootx.job;

import com.bootx.util.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PddPublishLogJob {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0/1 * *  * ?")
    public void clear(){
        Integer count = jdbcTemplate.update("delete from pddpublishlog where createdDate<='"+ DateUtils.formatDateToString(DateUtils.getNextDay(-1),"yyyy-MM-dd HH:mm:ss") +"'");
        System.out.println(count);
    }

}
