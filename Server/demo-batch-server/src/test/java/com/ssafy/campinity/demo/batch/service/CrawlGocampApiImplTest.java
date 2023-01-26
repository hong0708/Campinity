package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CrawlGocampApiImplTest {


    @Autowired
    private CrawlGocampApi crawlGocampApi;


    void getCampsiteListTest() throws UnsupportedEncodingException, JsonProcessingException {

        int numOfRows = 10;

        List<ResCampsiteDto> campsiteList = crawlGocampApi.getCampsiteList(numOfRows);
        assertEquals(numOfRows, campsiteList.size(), "gocamp API의 campsite list 요청이 실패했습니다.");

    }

}