
package com.bootx.pdd.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.*;
import com.bootx.pdd.dao.PddCrawlerProductDao;
import com.bootx.pdd.entity.*;
import com.bootx.pdd.service.PddCrawlerProductService;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Override
    public void update(CrawlerProduct crawlerProduct, PddCrawlerProduct pddCrawlerProduct) {
        if(crawlerProduct!=null&&pddCrawlerProduct!=null&&!crawlerProduct.isNew()&&!pddCrawlerProduct.isNew()){
            if(crawlerProduct.getStatus()==1){
                pddCrawlerProduct.setPublishStatus(0);
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
}