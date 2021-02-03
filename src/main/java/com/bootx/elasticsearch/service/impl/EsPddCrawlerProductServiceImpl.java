package com.bootx.elasticsearch.service.impl;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.elasticsearch.entity.EsPddCrawlerProduct;
import com.bootx.elasticsearch.repository.EsPddCrawlerProductRepository;
import com.bootx.elasticsearch.service.EsPddCrawlerProductService;
import com.bootx.entity.Member;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.service.PddPublishLogService;
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
public class EsPddCrawlerProductServiceImpl extends EsBaseServiceImpl implements EsPddCrawlerProductService {

    @Resource
    private EsPddCrawlerProductRepository esPddCrawlerProductRepository;
    @Resource
    private PddPublishLogService pddPublishLogService;

    @Override
    public void add(PddCrawlerProduct pddCrawlerProduct) {
        EsPddCrawlerProduct esPddCrawlerProduct = new EsPddCrawlerProduct();
        esPddCrawlerProduct.setId(pddCrawlerProduct.getId());
        esPddCrawlerProduct.setBatchId(pddCrawlerProduct.getBatchId());
        esPddCrawlerProduct.setCrawlerProductId(pddCrawlerProduct.getCrawlerProduct().getId());
        esPddCrawlerProduct.setSn(pddCrawlerProduct.getSn());
        esPddCrawlerProduct.setName(pddCrawlerProduct.getName());
        esPddCrawlerProduct.setPrice(pddCrawlerProduct.getPrice());
        esPddCrawlerProduct.setStock(pddCrawlerProduct.getStock());
        esPddCrawlerProduct.setPluginId(pddCrawlerProduct.getPluginId());
        esPddCrawlerProduct.setUrl(pddCrawlerProduct.getUrl());
        esPddCrawlerProduct.setMemberId(pddCrawlerProduct.getMember().getId());
        esPddCrawlerProduct.setStatus(pddCrawlerProduct.getStatus());
        esPddCrawlerProduct.setIsDeleted(pddCrawlerProduct.getIsDeleted());
        esPddCrawlerProduct.setPublishStatus(pddCrawlerProduct.getPublishStatus());
        esPddCrawlerProduct.setPddLogs(pddPublishLogService.query(pddCrawlerProduct));
        esPddCrawlerProduct.setImage(pddCrawlerProduct.getImage());
        esPddCrawlerProduct.setCreatedDate(pddCrawlerProduct.getCreatedDate());
        esPddCrawlerProduct.setLastModifiedDate(pddCrawlerProduct.getLastModifiedDate());
        if(pddCrawlerProduct.getProductCategory()!=null){
            esPddCrawlerProduct.setProductCategoryId(pddCrawlerProduct.getProductCategory().getId());
            esPddCrawlerProduct.setProductCategoryName(pddCrawlerProduct.getProductCategoryName());
        }
        esPddCrawlerProductRepository.save(esPddCrawlerProduct);
    }

    @Override
    public void remove(PddCrawlerProduct pddCrawlerProduct) {
        esPddCrawlerProductRepository.delete(esPddCrawlerProductRepository.findById(pddCrawlerProduct.getId()).get());
    }

    @Override
    public void remove(Long id) {
        esPddCrawlerProductRepository.delete(esPddCrawlerProductRepository.findById(id).get());
    }

    @Override
    public Page<EsPddCrawlerProduct> findPage(Pageable pageable, String name, String sn, Integer status,Integer publishStatus,Boolean isDeleted, Date beginDate, Date endDate, Member member) throws IOException {
        if(member==null){
            return new Page<EsPddCrawlerProduct>(Collections.emptyList(),0L,pageable);
        }


        SearchRequest searchRequest = new SearchRequest();
        List<Map<String,Object>> esPddCrawlerProducts = new ArrayList<>();
        searchRequest.indices("pdd_crawler_product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("memberId",member.getId()));
        if(StringUtils.isNotBlank(name)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name",name));
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            highlightBuilder.preTags("<span class=\"keywords\">");
            highlightBuilder.postTags("</span>");
            highlightBuilder.field("name");
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        if(StringUtils.isNotBlank(sn)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("sn",sn));
        }
        if(status!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("status",status));
        }
        if(publishStatus!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("publishStatus",publishStatus));
        }
        if(isDeleted!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted",isDeleted));
        }
        if(isDeleted!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted",isDeleted));
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("createdDate", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);

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