package com.bootx.pdd.service.impl;

import com.bootx.elasticsearch.service.impl.EsPddPublishLogServiceImpl;
import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddPublishLog;
import com.bootx.pdd.service.PddPublishLogService;
import com.bootx.service.impl.BaseServiceImpl;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PddPublishLogServiceImpl extends BaseServiceImpl<PddPublishLog,Long> implements PddPublishLogService {

    @Resource
    private EsPddPublishLogServiceImpl esPddPublishLogService;

    @Override
    public PddPublishLog create(Long sn,PddCrawlerProduct product, Store store, Map<String, Object> map,PopBaseHttpResponse.ErrorResponse errorResponse) {
        PddPublishLog pddPublishLog = new PddPublishLog();
        pddPublishLog.setStatus(1);
        pddPublishLog.setCrawlerProduct(product);
        pddPublishLog.setStore(store);
        pddPublishLog.setResult(map);
        pddPublishLog.setAccessToken(store.getAccessToken());
        if(errorResponse!=null){
            map.put("errorCode",errorResponse.getErrorCode());
            map.put("requestId",errorResponse.getRequestId());
            map.put("errorMsg",errorResponse.getErrorMsg());
            map.put("subMsg",errorResponse.getSubMsg());
            map.put("subCode",errorResponse.getSubCode());
            pddPublishLog.setCode(errorResponse.getErrorCode());
            pddPublishLog.setMsg(errorResponse.getErrorMsg());
            pddPublishLog.setStatus(3);
        }else {
            pddPublishLog.setCode(0);
            pddPublishLog.setMsg("ok");
            pddPublishLog.setStatus(2);
        }
        if(pddPublishLog.getStore()!=null){
            pddPublishLog.setStoreName(pddPublishLog.getStore().getMallName());
            pddPublishLog.setProductName(pddPublishLog.getCrawlerProduct().getName());
        }
        pddPublishLog.setSn(sn);
        pddPublishLog = super.save(pddPublishLog);
        // 写入es
        esPddPublishLogService.add(pddPublishLog);
        return pddPublishLog;
    }

    @Override
    public PddPublishLog create1(Long sn,PddCrawlerProduct product, Store store) {
        PddPublishLog pddPublishLog = new PddPublishLog();
        pddPublishLog.setCrawlerProduct(product);
        pddPublishLog.setStore(store);
        pddPublishLog.setStatus(1);
        pddPublishLog.setAccessToken(store.getAccessToken());
        if(pddPublishLog.getStore()!=null){
            pddPublishLog.setStoreName(pddPublishLog.getStore().getMallName());
            pddPublishLog.setProductName(pddPublishLog.getCrawlerProduct().getName());
        }
        pddPublishLog.setSn(sn);
        pddPublishLog = super.save(pddPublishLog);
        // 写入es
        esPddPublishLogService.add(pddPublishLog);
        return pddPublishLog;
    }

    @Override
    public PddPublishLog update(PddPublishLog pddPublishLog,PddCrawlerProduct product, Store store, Map<String, Object> map,PopBaseHttpResponse.ErrorResponse errorResponse) {
        if(pddPublishLog==null){
            return create(System.currentTimeMillis(),product,store,map,errorResponse);
        }
        pddPublishLog.setResult(map);
        if(errorResponse!=null){
            map.put("errorCode",errorResponse.getErrorCode());
            map.put("requestId",errorResponse.getRequestId());
            map.put("errorMsg",errorResponse.getErrorMsg());
            map.put("subMsg",errorResponse.getSubMsg());
            map.put("subCode",errorResponse.getSubCode());
            pddPublishLog.setCode(errorResponse.getErrorCode());
            pddPublishLog.setMsg(errorResponse.getErrorMsg());
            pddPublishLog.setStatus(3);
        }else {
            pddPublishLog.setCode(0);
            pddPublishLog.setStatus(2);
            pddPublishLog.setMsg("ok");
        }
        if(pddPublishLog.getStore()!=null){
            pddPublishLog.setStoreName(pddPublishLog.getStore().getMallName());
            pddPublishLog.setProductName(pddPublishLog.getCrawlerProduct().getName());
        }
        pddPublishLog = super.update(pddPublishLog);
        // 更新es
        esPddPublishLogService.add(pddPublishLog);
        return pddPublishLog;
    }

    @Override
    public PddPublishLog update1(PddPublishLog pddPublishLog,PddCrawlerProduct product, Store store, Map<String, Object> map,PopBaseHttpResponse.ErrorResponse errorResponse) {
        if(pddPublishLog==null){
            return create(System.currentTimeMillis(),product,store,map,errorResponse);
        }
        pddPublishLog.setResult(map);
        if(errorResponse!=null){
            map.put("errorCode",errorResponse.getErrorCode());
            map.put("requestId",errorResponse.getRequestId());
            map.put("errorMsg",errorResponse.getErrorMsg());
            map.put("subMsg",errorResponse.getSubMsg());
            map.put("subCode",errorResponse.getSubCode());
            pddPublishLog.setCode(errorResponse.getErrorCode());
            pddPublishLog.setMsg(errorResponse.getErrorMsg());
        }else {
            pddPublishLog.setCode(0);
            pddPublishLog.setMsg("ok");
        }
        if(pddPublishLog.getStore()!=null){
            pddPublishLog.setStoreName(pddPublishLog.getStore().getMallName());
            pddPublishLog.setProductName(pddPublishLog.getCrawlerProduct().getName());
        }
        pddPublishLog = super.update(pddPublishLog);
        // 更新es
        esPddPublishLogService.add(pddPublishLog);
        return pddPublishLog;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> query(PddCrawlerProduct pddCrawlerProduct) {
        StringBuffer sql = new StringBuffer();

        sql.append("select code,msg,storename storeName,productname productName from pddpublishlog where 1=1");
        if(pddCrawlerProduct!=null){
            sql.append(" and crawlerProduct_id="+pddCrawlerProduct.getId());
            sql.append(" and sn = (select max(sn) from pddpublishlog where crawlerProduct_id="+pddCrawlerProduct.getId()+")");
        }
        return jdbcTemplate.queryForList(sql.toString());
    }
}
