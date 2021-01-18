
package com.bootx.pdd.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.elasticsearch.service.EsPddCrawlerProductService;
import com.bootx.entity.*;
import com.bootx.pdd.dao.PddCrawlerProductDao;
import com.bootx.pdd.entity.*;
import com.bootx.pdd.service.PddCrawlerProductService;
import com.bootx.pdd.service.PddGoodsService;
import com.bootx.pdd.service.PddLogService;
import com.bootx.service.StoreService;
import com.bootx.service.impl.BaseServiceImpl;
import com.pdd.pop.sdk.http.api.pop.response.PddGoodsAddResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class PddCrawlerProductServiceImpl extends BaseServiceImpl<PddCrawlerProduct, Long> implements PddCrawlerProductService {

    @Resource
    private PddCrawlerProductDao pddCrawlerProductDao;
    @Resource
    private PddGoodsService goodsService;
    @Resource
    private StoreService storeService;
    @Resource
    private PddLogService pddLogService;
    @Resource
    private EsPddCrawlerProductService esPddCrawlerProductService;

    @Override
    public void update(CrawlerProduct crawlerProduct, PddCrawlerProduct pddCrawlerProduct) {
        if(crawlerProduct!=null&&pddCrawlerProduct!=null&&!crawlerProduct.isNew()&&!pddCrawlerProduct.isNew()){
            if(crawlerProduct.getStatus()==1){
                pddCrawlerProduct.setPublishStatus(10);
            }else{
                pddCrawlerProduct.setPublishStatus(null);
            }
            pddCrawlerProduct.setName(crawlerProduct.getName());
            pddCrawlerProduct.setProductCategory(crawlerProduct.getProductCategory());
            pddCrawlerProduct.setStock(crawlerProduct.getStock());
            pddCrawlerProduct.setStatus(crawlerProduct.getStatus());
            pddCrawlerProduct.setSn(crawlerProduct.getSn());
            pddCrawlerProduct.setProductCategoryNames(crawlerProduct.getProductCategoryNames());
            pddCrawlerProduct.setProductCategoryIds(crawlerProduct.getProductCategoryIds());
            pddCrawlerProduct.setPrice(crawlerProduct.getPrice());
            pddCrawlerProduct.setName(crawlerProduct.getName());
            pddCrawlerProduct.setCrawlerProduct(crawlerProduct);
            pddCrawlerProduct.setCrawlerProductImage(crawlerProductImage(pddCrawlerProduct,crawlerProduct.getCrawlerProductImage()));
            pddCrawlerProduct.setCrawlerProductIntroduction(crawlerProductIntroduction(pddCrawlerProduct,crawlerProduct.getCrawlerProductIntroduction()));
            pddCrawlerProduct.setCrawlerProductIntroductionImage(crawlerProductIntroductionImage(pddCrawlerProduct,crawlerProduct.getCrawlerProductIntroductionImage()));
            pddCrawlerProduct.setCrawlerProductParameterValue(crawlerProductParameterValue(pddCrawlerProduct,crawlerProduct.getCrawlerProductParameterValue()));
            pddCrawlerProduct.setCrawlerProductSku(crawlerProductSku(pddCrawlerProduct,crawlerProduct.getCrawlerProductSku()));
            pddCrawlerProduct.setCrawlerProductSpecification(crawlerProductSpecification(pddCrawlerProduct,crawlerProduct.getCrawlerProductSpecification()));
            super.update(pddCrawlerProduct);
        }
    }


    private PddCrawlerProductImage crawlerProductImage(PddCrawlerProduct pddCrawlerProduct, CrawlerProductImage crawlerProductImage) {
        PddCrawlerProductImage pddCrawlerProductImage = pddCrawlerProduct.getCrawlerProductImage();
        pddCrawlerProductImage.setCrawlerProduct(pddCrawlerProduct);
        pddCrawlerProductImage.setImages(crawlerProductImage.getImages());

        return pddCrawlerProductImage;
    }

    private PddCrawlerProductIntroduction crawlerProductIntroduction(PddCrawlerProduct pddCrawlerProduct, CrawlerProductIntroduction crawlerProductIntroduction) {
        PddCrawlerProductIntroduction pddCrawlerProductIntroduction = pddCrawlerProduct.getCrawlerProductIntroduction();
        pddCrawlerProductIntroduction.setCrawlerProduct(pddCrawlerProduct);
        pddCrawlerProductIntroduction.setContent(crawlerProductIntroduction.getContent());
        return pddCrawlerProductIntroduction;
    }

    private PddCrawlerProductIntroductionImage crawlerProductIntroductionImage(PddCrawlerProduct pddCrawlerProduct, CrawlerProductIntroductionImage crawlerProductIntroductionImage) {
        PddCrawlerProductIntroductionImage pddCrawlerProductIntroductionImage = pddCrawlerProduct.getCrawlerProductIntroductionImage();
        pddCrawlerProductIntroductionImage.setCrawlerProduct(pddCrawlerProduct);
        pddCrawlerProductIntroductionImage.setImages(crawlerProductIntroductionImage.getImages());
        return pddCrawlerProductIntroductionImage;
    }


    private PddCrawlerProductParameterValue crawlerProductParameterValue(PddCrawlerProduct pddCrawlerProduct, CrawlerProductParameterValue crawlerProductParameterValue) {
        PddCrawlerProductParameterValue pddCrawlerProductParameterValue = pddCrawlerProduct.getCrawlerProductParameterValue();
        pddCrawlerProductParameterValue.setCrawlerProduct(pddCrawlerProduct);
        pddCrawlerProductParameterValue.setParameterValues(crawlerProductParameterValue.getParameterValues());
        return pddCrawlerProductParameterValue;


    }

    private PddCrawlerProductSku crawlerProductSku(PddCrawlerProduct pddCrawlerProduct,CrawlerProductSku crawlerProductSku) {
        PddCrawlerProductSku pddCrawlerProductSku = pddCrawlerProduct.getCrawlerProductSku();
        pddCrawlerProductSku.setSkus(crawlerProductSku.getSkus());
        pddCrawlerProductSku.setCrawlerProduct(pddCrawlerProduct);
        return pddCrawlerProductSku;

    }

    private PddCrawlerProductSpecification crawlerProductSpecification(PddCrawlerProduct pddCrawlerProduct,CrawlerProductSpecification crawlerProductSpecification) {
        PddCrawlerProductSpecification pddCrawlerProductSpecification = pddCrawlerProduct.getCrawlerProductSpecification();
        crawlerProductSpecification.getCrawlerSpecifications().stream().forEach(item->{
            PddCrawlerSpecification pddCrawlerSpecification = new PddCrawlerSpecification();
            pddCrawlerSpecification.setEntries(item.getEntries().stream().map(i-> new PddCrawlerSpecification.Entry(i.getName(),i.getValue(),i.getImg())).collect(Collectors.toList()));
            pddCrawlerSpecification.setName(item.getName());
            pddCrawlerSpecification.setOptions(item.getOptions());
            pddCrawlerProductSpecification.getCrawlerSpecifications().add(pddCrawlerSpecification);
        });
        pddCrawlerProductSpecification.setCrawlerProduct(pddCrawlerProduct);
        return pddCrawlerProductSpecification;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PddCrawlerProduct> findPage(Pageable pageable, String name, String sn, Integer status,Integer publishStatus,Boolean isDeleted, Date beginDate, Date endDate, Member member) {
        return pddCrawlerProductDao.findPage(pageable,name,sn,status,publishStatus,isDeleted,beginDate,endDate,member);
    }



    @Override
    @Async
    public void publish(Long[] ids, Long[] storeIds) throws Exception {
        Long sn = System.currentTimeMillis();
        List<PddCrawlerProduct> pddCrawlerProducts = findList(ids);
        List<Store> stores = storeService.findList(storeIds);
        for (PddCrawlerProduct product:pddCrawlerProducts) {
            for (Store store:stores) {
                PddGoodsAddResponse pddGoodsAddResponse = goodsService.pddGoodsAdd(product, store.getAccessToken());
                Map<String,Object> map = new HashMap<>();
                if(pddGoodsAddResponse.getGoodsAddResponse()!=null){
                    PddGoodsAddResponse.GoodsAddResponse goodsAddResponse = pddGoodsAddResponse.getGoodsAddResponse();
                    map.put("goodsId",goodsAddResponse.getGoodsId());
                    map.put("goodsCommitId",goodsAddResponse.getGoodsCommitId());
                }
                pddLogService.create(sn,product,store,map,pddGoodsAddResponse.getErrorResponse());
            }
        }
    }

    @Override
    public Map<String, Object> detail(Long id) {
        Map<String,Object> data = new HashMap<>();
        return data;
    }

    @Override
    public PddCrawlerProduct save(PddCrawlerProduct pddCrawlerProduct) {
        esPddCrawlerProductService.add(pddCrawlerProduct);
        return super.save(pddCrawlerProduct);
    }

    @Override
    public PddCrawlerProduct update(PddCrawlerProduct pddCrawlerProduct) {
        esPddCrawlerProductService.add(pddCrawlerProduct);
        return super.update(pddCrawlerProduct);
    }

    @Override
    public PddCrawlerProduct update(PddCrawlerProduct pddCrawlerProduct, String... ignoreProperties) {
        esPddCrawlerProductService.add(pddCrawlerProduct);
        return super.update(pddCrawlerProduct, ignoreProperties);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    public void delete(PddCrawlerProduct pddCrawlerProduct) {
        super.delete(pddCrawlerProduct);
    }
}