package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlGocampApiTest {


    @Autowired
    private CrawlGocampApi crawlGocampApi;

    @Test
    void getCampsiteListTest() throws UnsupportedEncodingException, JsonProcessingException {

        crawlGocampApi.getCampsiteList();


    }

}