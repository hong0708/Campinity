package com.ssafy.campinity.demo.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan("com.ssafy.campinity.core")
@ComponentScan("com.ssafy.campinity.demo.batch.campinityRepository")
public class TestBatchConfig {}
