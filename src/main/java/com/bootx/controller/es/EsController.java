package com.bootx.controller.es;

import com.bootx.elasticsearch.service.EsPddCrawlerProductService;
import com.bootx.pdd.entity.PddCrawlerProduct;
import com.bootx.pdd.service.PddCrawlerProductService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/es")
@CrossOrigin
public class EsController {

    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private EsPddCrawlerProductService esPddCrawlerProductService;
    @Resource
    private PddCrawlerProductService pddCrawlerProductService;


    @GetMapping("/delete")
    public AcknowledgedResponse delete(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();
        deleteIndexRequest.indices(index);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return delete;
    }
    @GetMapping("/product")
    public void product(String index) throws IOException {
        List<PddCrawlerProduct> all = pddCrawlerProductService.findAll();
        for (PddCrawlerProduct product:all) {
            esPddCrawlerProductService.add(product);
        }
    }
}
