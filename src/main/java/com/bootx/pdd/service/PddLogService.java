
package com.bootx.pdd.service;

import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.entity.PddLog;
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
public interface PddLogService extends BaseService<PddLog, Long> {

    void create(Long sn,PddCrawlerProduct product, Store store, Map<String, Object> map, PopBaseHttpResponse.ErrorResponse errorResponse);

    List<Map<String, Object>> query(PddCrawlerProduct pddCrawlerProduct);
}