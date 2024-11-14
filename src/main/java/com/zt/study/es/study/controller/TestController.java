package com.zt.study.es.study.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.Alias;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author zhengtao on 2023/12/2
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    ElasticsearchClient client;

    @PostMapping("/createIndex")
    public String createIndex() throws IOException {
        // CreateIndexResponse response = client.indices().create(builder -> builder
        //         .settings(indexSettingsBuilder -> indexSettingsBuilder.numberOfReplicas("1").numberOfShards("2"))
        //         .mappings(typeMappingBuilder -> typeMappingBuilder
        //                 .properties("age", propertyBuilder -> propertyBuilder.integer(integerNumberPropertyBuilder -> integerNumberPropertyBuilder))
        //                 .properties("name", propertyBuilder -> propertyBuilder.keyword(keywordPropertyBuilder -> keywordPropertyBuilder))
        //                 .properties("poems", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word").searchAnalyzer("ik_smart")))
        //                 .properties("about", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word").searchAnalyzer("ik_smart")))
        //                 .properties("success", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word").searchAnalyzer("ik_smart")))
        //         )
        //         .index("javaEs"));

        CreateIndexResponse createResponse = client.indices().create(
                new CreateIndexRequest.Builder()
                        .index("my-index")
                        .aliases("foo",
                                new Alias.Builder().isWriteIndex(true).build()
                        )
                        .build()
        );
        log.info("response={}", createResponse);
        return "hello world";
    }

    @RequestMapping("/workflow")
    public String workflow() {
        return "hello workflow!！";
    }
}
