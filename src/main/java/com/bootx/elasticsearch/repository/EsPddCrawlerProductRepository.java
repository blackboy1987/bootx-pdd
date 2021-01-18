package com.bootx.elasticsearch.repository;

import com.bootx.elasticsearch.entity.EsPddCrawlerProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsPddCrawlerProductRepository extends ElasticsearchRepository<EsPddCrawlerProduct, Long> {
}