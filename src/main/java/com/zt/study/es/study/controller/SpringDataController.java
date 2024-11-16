package com.zt.study.es.study.controller;

import com.zt.study.es.study.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring data 使用
 *
 * @author zhengtao on 2024/11/16
 */
@RestController
@Slf4j
public class SpringDataController {
    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @PostMapping("/create")
    public Book create(@RequestBody Book book) {
        elasticsearchOperations.save(book);
        return book;
    }

    @PostMapping("/query")
    public Book query(@RequestBody Book book) {
        return elasticsearchOperations.get(book.getId(), Book.class);
    }

    @PostMapping("/delete")
    public Book delete(@RequestBody Book book) {
        elasticsearchOperations.delete(book.getId(), Book.class);
        return book;
    }

    @PostMapping("/update")
    public Book update(@RequestBody Book book) {
        Document document = Document.create();
        document.put("author", book.getAuthor());
        document.put("price", book.getPrice());
        document.put("publish", book.getPublish());
        document.put("name", book.getName());
        document.put("info", book.getInfo());
        UpdateQuery updateQuery = UpdateQuery
                .builder("1")
                .withDocument(document)
                .withDocAsUpsert(true)
                .build();
        elasticsearchOperations.update(updateQuery, IndexCoordinates.of("books"));
        return book;
    }
}
