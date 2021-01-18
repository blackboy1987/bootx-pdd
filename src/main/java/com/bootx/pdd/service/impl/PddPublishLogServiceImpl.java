package com.bootx.pdd.service.impl;

import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddLog;
import com.bootx.pdd.entity.PddPublishLog;
import com.bootx.pdd.service.PddLogService;
import com.bootx.pdd.service.PddPublishLogService;
import com.bootx.service.impl.BaseServiceImpl;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PddPublishLogServiceImpl extends BaseServiceImpl<PddPublishLog,Long> implements PddPublishLogService {

    @Override
    public void create(Long sn,PddCrawlerProduct product, Store store, Map<String, Object> map,PopBaseHttpResponse.ErrorResponse errorResponse) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> query(PddCrawlerProduct pddCrawlerProduct) {
        StringBuffer sql = new StringBuffer();

        sql.append("select code,msg,storename storeName,productname productName from pddlog where 1=1");
        if(pddCrawlerProduct!=null){
            sql.append(" and crawlerProduct_id="+pddCrawlerProduct.getId());
            sql.append(" and sn = (select max(sn) from pddlog where crawlerProduct_id="+pddCrawlerProduct.getId()+")");
        }
        return jdbcTemplate.queryForList(sql.toString());
    }
}
