package com.bootx.pdd.service.impl;

import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddLog;
import com.bootx.pdd.service.PddLogService;
import com.bootx.service.impl.BaseServiceImpl;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PddPublishLogServiceImpl extends BaseServiceImpl<PddLog,Long> implements PddLogService {

    @Override
    public void create(Long sn,PddCrawlerProduct product, Store store, Map<String, Object> map,PopBaseHttpResponse.ErrorResponse errorResponse) {
        PddLog pddLog = new PddLog();
        pddLog.setCrawlerProduct(product);
        pddLog.setStore(store);
        pddLog.setResult(map);
        pddLog.setAccessToken(store.getAccessToken());
        if(errorResponse!=null){
            map.put("errorCode",errorResponse.getErrorCode());
            map.put("requestId",errorResponse.getRequestId());
            map.put("errorMsg",errorResponse.getErrorMsg());
            map.put("subMsg",errorResponse.getSubMsg());
            map.put("subCode",errorResponse.getSubCode());
            pddLog.setCode(errorResponse.getErrorCode());
            pddLog.setMsg(errorResponse.getErrorMsg());
        }else {
            pddLog.setCode(0);
            pddLog.setMsg("ok");
        }
        if(pddLog.getStore()!=null){
            pddLog.setStoreName(pddLog.getStore().getMallName());
            pddLog.setProductName(pddLog.getCrawlerProduct().getName());
        }
        pddLog.setSn(sn);
        super.save(pddLog);
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
