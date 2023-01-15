package com.ssafy.campinity.demo.batch.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.demo.batch.dto.GocampClient;
import com.ssafy.campinity.demo.batch.dto.gocamp.req.ReqCampsiteDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteListDto;
import com.ssafy.campinity.demo.batch.service.CrawlGocampApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlGocampApiImpl implements CrawlGocampApi {

    private final GocampClient gocampClient;

    @Override
    public List<ResCampsiteDto> getCampsiteList(int numOfRows) throws UnsupportedEncodingException, JsonProcessingException {

        var reqCampsiteDto = new ReqCampsiteDto();
        ResCampsiteListDto response = gocampClient.RequestGocampApi(numOfRows, reqCampsiteDto);

        return response.getResponse().getBody().getItems().getItem();

    }

}
