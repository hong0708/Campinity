package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteImageDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteListDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CrawlGocampApi {

    List<ResCampsiteDto> getCampsiteList(int numOfRows) throws UnsupportedEncodingException, JsonProcessingException;

    List<ResCampsiteImageDto> getCampsiteImageList(int numOfRows, int contentId) throws UnsupportedEncodingException, JsonProcessingException;
}
