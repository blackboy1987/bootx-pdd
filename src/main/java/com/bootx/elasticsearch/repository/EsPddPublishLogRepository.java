package com.bootx.elasticsearch.repository;

import com.bootx.elasticsearch.entity.EsPddPublishLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsPddPublishLogRepository extends ElasticsearchRepository<EsPddPublishLog, Long> {
}