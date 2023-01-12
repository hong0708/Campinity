package com.ssafy.campinity.demo.batch.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.demo.batch.gocamp.GocampClient;
import com.ssafy.campinity.demo.batch.gocamp.dto.req.ReqCampsiteDto;
import com.ssafy.campinity.demo.batch.gocamp.dto.res.ResCampsiteListDto;
import com.ssafy.campinity.demo.batch.service.CrawlGocampApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class CrawlGocampApiImpl implements CrawlGocampApi {

    private final GocampClient gocampClient;

    public void getCampsiteList() throws UnsupportedEncodingException, JsonProcessingException {
        var reqCampsiteDto = new ReqCampsiteDto();
        ResCampsiteListDto response = gocampClient.RequestGocampApi(reqCampsiteDto);

        System.out.println(response.getResponse().getBody().getItems().getItem());
    }

}
