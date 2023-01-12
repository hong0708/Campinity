package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.demo.batch.service.impl.CrawlGocampApiImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

@SpringBootTest
class CrawlGocampApiImplTest {


    @Autowired
    private CrawlGocampApiImpl crawlGocampApiImpl;

    @Test
    void getCampsiteListTest() throws UnsupportedEncodingException, JsonProcessingException {

        crawlGocampApiImpl.getCampsiteList();


    }

}