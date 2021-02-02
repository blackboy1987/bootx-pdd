package com.bootx.elasticsearch.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.elasticsearch.entity.EsPddPublishLog;
import com.bootx.elasticsearch.repository.EsPddPublishLogRepository;
import com.bootx.elasticsearch.service.EsPddPublishLogService;
import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddPublishLog;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class EsPddPublishLogServiceImpl extends EsBaseServiceImpl implements EsPddPublishLogService {

    @Resource
    private EsPddPublishLogRepository esPddPublishLogRepository;

    @Override
    public void add(PddPublishLog pddPublishLog) {
        EsPddPublishLog esPddPublishLog = new EsPddPublishLog();
        esPddPublishLog.setId(pddPublishLog.getId());
        esPddPublishLog.setStoreId(pddPublishLog.getStore().getId());
        esPddPublishLog.setStoreName(pddPublishLog.getStoreName());
        esPddPublishLog.setMemberId(pddPublishLog.getStore().getMember().getId());
        esPddPublishLog.setProductImage(pddPublishLog.getCrawlerProduct().getImage());
        esPddPublishLog.setProductId(pddPublishLog.getCrawlerProduct().getId());
        esPddPublishLog.setProductName(pddPublishLog.getCrawlerProduct().getName());
        esPddPublishLog.setProductSn(pddPublishLog.getCrawlerProduct().getName());
        esPddPublishLog.setProductPrice(pddPublishLog.getCrawlerProduct().getPrice());
        esPddPublishLog.setProductCategoryId(pddPublishLog.getCrawlerProduct().getProductCategory().getId());
        esPddPublishLog.setProductCategoryName(pddPublishLog.getCrawlerProduct().getProductCategoryName());
        esPddPublishLog.setSn(pddPublishLog.getSn());
        esPddPublishLog.setAccessToken(pddPublishLog.getAccessToken());
        esPddPublishLog.setCode(pddPublishLog.getCode());
        esPddPublishLog.setMsg(pddPublishLog.getMsg());
        esPddPublishLog.setStatus(pddPublishLog.getStatus());
        esPddPublishLog.setCreatedDate(pddPublishLog.getCreatedDate());
        esPddPublishLog.setLastModifiedDate(pddPublishLog.getLastModifiedDate());
        esPddPublishLogRepository.save(esPddPublishLog);
    }

    @Override
    public void remove(PddPublishLog pddPublishLog) {
        esPddPublishLogRepository.delete(esPddPublishLogRepository.findById(pddPublishLog.getId()).get());
    }

    @Override
    public void remove(Long id) {
        esPddPublishLogRepository.delete(esPddPublishLogRepository.findById(id).get());
    }

    @Override
    public Page<EsPddPublishLog> findPage(Pageable pageable, String name, Integer status, Date beginDate, Date endDate, Store store) throws IOException {
        if(store==null){
            return new Page<>(Collections.emptyList(),0L,pageable);
        }


        SearchRequest searchRequest = new SearchRequest();
        List<Map<String,Object>> esPddCrawlerProducts = new ArrayList<>();
        searchRequest.indices("pdd_publish_log");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("storeId",store.getId()));
        if(StringUtils.isNotBlank(name)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name",name));
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            highlightBuilder.preTags("<span class=\"keywords\">");
            highlightBuilder.postTags("</span>");
            highlightBuilder.field("name");
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        if(status!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("status",status));
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.sort("createdDate", SortOrder.DESC);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit:hits1) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            for (String key:highlightFields.keySet()) {
                sourceAsMap.put(key,highlightFields.get(key).getFragments()[0].string());
            }
            esPddCrawlerProducts.add(sourceAsMap);
        }
        return new Page(esPddCrawlerProducts,hits.getTotalHits().value,pageable);
    }
}