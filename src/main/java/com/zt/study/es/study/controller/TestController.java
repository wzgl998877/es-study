package com.zt.study.es.study.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.zt.study.es.study.vo.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengtao on 2023/12/2
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    ElasticsearchClient esClient;

    @PostMapping("/createIndex")
    public IndexResponse createIndex() throws IOException {
        Goods goods = Goods.builder().skuId("1").skuName("苹果手机").price("100000").num("100").build();
        return esClient.index(i -> i
                .index("java-es")
                .id(goods.getSkuId())
                .document(goods)
        );
    }

    @PostMapping("/queryIndex")
    public Goods queryIndex() throws IOException {
        GetResponse<Goods> response = esClient.get(g -> g
                        .index("java-es")
                        .id("1"),
                Goods.class
        );
        return response.source();
    }

    @PostMapping("/searchIndex")
    public List<Goods> searchIndex() throws IOException {
        SearchResponse<Goods> response = esClient.search(g -> g
                        .index("java-es")
                        .query(q -> q
                                .match(t -> t
                                        .field("skuName")
                                        .query(FieldValue.of("苹果"))
                                )
                        ),
                Goods.class
        );
        List<Hit<Goods>> hits = response.hits().hits();
        List<Goods> goods = new ArrayList<>();
        hits.forEach(hit -> goods.add(hit.source()));
        return goods;
    }

    @PostMapping("/updateIndex")
    public Goods updateIndex() throws IOException {
        Goods goods = Goods.builder().skuId("2").skuName("苹果16").price("100000").num("100").build();
        esClient.update(u -> u
                        .index("java-es")
                        .id("1")
                        .doc(goods)
                        .upsert(goods),
                Goods.class
        );
        return goods;
    }

    @PostMapping("/deleteIndex")
    public void deleteIndex() throws IOException {
        esClient.delete(d -> d.index("java-es").id("1"));
    }
}
