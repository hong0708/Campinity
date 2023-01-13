package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.demo.batch.gocamp.dto.res.ResCampsiteDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CrawlGocampApi {

    List<ResCampsiteDto> getCampsiteList(int numOfRows) throws UnsupportedEncodingException, JsonProcessingException;
}
