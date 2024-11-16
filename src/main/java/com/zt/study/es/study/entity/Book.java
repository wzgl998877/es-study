package com.zt.study.es.study.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
//indexName 创建文档名 book
//shards 分片数 2
//replicas 副本数 2
//createIndex 若没有索引会自动创建
@Document(indexName = "books", shards = 3, replicas = 2, createIndex = true)
public class Book {
    //文档唯一id
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private String price;

    @Field(type = FieldType.Keyword)
    private String publish;

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word")
    private String info;
}