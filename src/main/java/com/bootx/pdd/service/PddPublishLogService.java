
package com.bootx.pdd.service;

import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddPublishLog;
import com.bootx.service.BaseService;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;

import java.util.List;
import java.util.Map;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PddPublishLogService extends BaseService<PddPublishLog, Long> {

    PddPublishLog create(Long sn,PddCrawlerProduct product, Store store, Map<String, Object> map, PopBaseHttpResponse.ErrorResponse errorResponse);
    PddPublishLog create1(Long sn,PddCrawlerProduct product, Store store);
    PddPublishLog update(PddPublishLog pddPublishLog,PddCrawlerProduct product, Store store, Map<String, Object> map, PopBaseHttpResponse.ErrorResponse errorResponse);
    List<Map<String, Object>> query(PddCrawlerProduct pddCrawlerProduct);

    PddPublishLog update1(PddPublishLog pddPublishLog,PddCrawlerProduct product, Store store, Map<String, Object> map, PopBaseHttpResponse.ErrorResponse errorResponse);
}