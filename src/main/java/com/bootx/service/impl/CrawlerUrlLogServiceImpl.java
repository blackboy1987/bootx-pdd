
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.CrawlerUrlLogDao;
import com.bootx.entity.*;
import com.bootx.pdd.service.GoodsService;
import com.bootx.service.CrawlerLogService;
import com.bootx.service.CrawlerUrlLogService;
import com.bootx.service.ProductService;
import com.bootx.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CrawlerUrlLogServiceImpl extends BaseServiceImpl<CrawlerUrlLog, Long> implements CrawlerUrlLogService {

    @Resource
    private CrawlerUrlLogDao crawlerUrlLogDao;
    @Resource
    private CrawlerLogService crawlerLogService;
    @Resource
    private ProductService productService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private StoreService storeService;

    @Override
    public CrawlerUrlLog findByUrl(String url) {
        return crawlerUrlLogDao.find("url",url);
    }

    @Override
    public void updateInfo(String url,Long productSn,String crawlerLogSn,String memo,Integer status) {
        CrawlerUrlLog crawlerUrlLog = findByUrlAndCrawlerLogSn(url,crawlerLogSn);
        if(crawlerUrlLog!=null){
            crawlerUrlLog.setStatus(status);
            crawlerUrlLog.setProductId(productSn);
            crawlerUrlLog.setMemo(memo);
            super.update(crawlerUrlLog);
            CrawlerLog crawlerLog = crawlerUrlLog.getCrawlerLog();
            if(status==1){
                // 成功
                crawlerLog.setSuccess(crawlerLog.getSuccess()+1);
            }else if(status==2){
                // 失败
                crawlerLog.setSuccess(crawlerLog.getFail()+1);
            }
            crawlerLogService.update(crawlerLog);
        }
    }

    @Override
    public void updateInfo(String url,String crawlerLogSn, String memo,Integer status) {
        CrawlerUrlLog crawlerUrlLog = findByUrlAndCrawlerLogSn(url,crawlerLogSn);
        if(crawlerUrlLog!=null){
            crawlerUrlLog.setStatus(status);
            crawlerUrlLog.setMemo(memo);
            super.update(crawlerUrlLog);
            CrawlerLog crawlerLog = crawlerUrlLog.getCrawlerLog();
            if(status==1){
                // 成功
                crawlerLog.setSuccess(crawlerLog.getSuccess()+1);
            }else if(status==2){
                // 失败
                crawlerLog.setSuccess(crawlerLog.getFail()+1);
            }
            crawlerLogService.update(crawlerLog);
        }
    }

    @Override
    public CrawlerUrlLog findByUrlAndCrawlerLogSn(String url, String crawlerLogSn) {
        return crawlerUrlLogDao.findByUrlAndCrawlerLogSn(url,crawlerLogSn);
    }

    @Override
    public Page<CrawlerUrlLog> findPage(Pageable pageable, Member member, Integer status) {
        return crawlerUrlLogDao.findPage(pageable,member,status);
    }

    @Override
    public void upload(Long id) throws Exception {
        CrawlerUrlLog crawlerUrlLog = find(id);
        Store store = storeService.find(4L);
        Product product = productService.find(crawlerUrlLog.getProductId());
        goodsService.pddGoodsAdd(product,store.getAccessToken());
    }
}