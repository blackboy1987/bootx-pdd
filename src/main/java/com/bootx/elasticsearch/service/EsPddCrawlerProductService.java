package com.bootx.elasticsearch.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.elasticsearch.entity.EsPddCrawlerProduct;
import com.bootx.entity.Member;
import com.bootx.pdd.entity.PddCrawlerProduct;

import java.io.IOException;
import java.util.Date;

public interface EsPddCrawlerProductService extends EsBaseService {

    void add(PddCrawlerProduct pddCrawlerProduct);

    void remove(PddCrawlerProduct pddCrawlerProduct);

    void remove(Long id);

    Page<EsPddCrawlerProduct> findPage(Pageable pageable, String name, String sn, Integer status,Integer publishStatus,Boolean isDeleted, Date beginDate, Date endDate, Member member) throws IOException;
}