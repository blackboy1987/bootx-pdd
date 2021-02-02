package com.bootx.elasticsearch.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.elasticsearch.entity.EsPddPublishLog;
import com.bootx.entity.Store;
import com.bootx.pdd.entity.PddPublishLog;

import java.io.IOException;
import java.util.Date;

public interface EsPddPublishLogService extends EsBaseService {

    void add(PddPublishLog pddPublishLog);

    void remove(PddPublishLog pddPublishLog);

    void remove(Long id);

    Page<EsPddPublishLog> findPage(Pageable pageable, String name, Integer status, Date beginDate, Date endDate, Store store) throws IOException;
}