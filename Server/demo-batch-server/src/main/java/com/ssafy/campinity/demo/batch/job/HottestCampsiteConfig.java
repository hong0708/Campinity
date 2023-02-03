package com.ssafy.campinity.demo.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.core.dto.CampsiteHottestRankingReqDTO;
import com.ssafy.campinity.core.dto.CampsiteRankingResDTO;
import com.ssafy.campinity.core.repository.campsite.custom.CampsiteCustomRepository;
import com.ssafy.campinity.core.repository.redis.RedisDao;
import com.ssafy.campinity.demo.batch.campinityRepository.CampinityCampsiteCustomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Configuration
@Slf4j
public class HottestCampsiteConfig {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CampinityCampsiteCustomRepository campinityCampsiteCustomRepository;

    @Bean
    public Job HottestCampsiteJob() {
        log.info("********** This is HottestCampsiteJob");

        return jobBuilderFactory.get("HottestCampsiteJob")
                .preventRestart()
                .start(HottestCampsiteStep())
                .build();
    }

    public Step HottestCampsiteStep() {
        log.info("********** This is HottestCampsiteStep");
        return stepBuilderFactory.get("HottestCampsiteStep")
                .tasklet(HottestCampsiteTasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet HottestCampsiteTasklet(@Value("#{jobParameters[time]}") String time) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                LocalDate today = LocalDate.now();
                LocalDate start = today.minusDays(7);
                LocalDate end = today.minusDays(1);

                List<CampsiteHottestRankingReqDTO> campsiteRankingDatas = campinityCampsiteCustomRepository.findCampsiteHottestRankingData(start, end);

                log.info("----------------start DATE: " + start);
                log.info("----------------end DATE: " + end);

                Collections.sort(campsiteRankingDatas, new Comparator<CampsiteHottestRankingReqDTO>() {
                    @Override
                    public int compare(CampsiteHottestRankingReqDTO o1, CampsiteHottestRankingReqDTO o2) {
                        return (3 * (o2.getQuestionCnt() + o2.getAnswerCnt()) + 2 * o2.getMessageCnt()) -
                                (3 * (o1.getQuestionCnt() + o1.getAnswerCnt()) + 2 * o1.getMessageCnt());
                    }
                });

                List<CampsiteRankingResDTO> result = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    CampsiteHottestRankingReqDTO camp = campsiteRankingDatas.get(i);
                    result.add(CampsiteRankingResDTO.builder().campsiteId(camp.getCampsiteId()).campName(camp.getCampName())
                            .doName(camp.getDoName()).sigunguName(camp.getSigunguName()).firstImageUrl(camp.getFirstImageUrl())
                            .ranking(i + 1).build());

                    log.info(camp.getCampsiteId() + " : " + camp.getAnswerCnt() + " " + camp.getQuestionCnt() + " " + camp.getMessageCnt());
                }

                ObjectMapper mapper = new ObjectMapper();
                String value = mapper.writeValueAsString(result);
                redisDao.setValues("hottestCampsite", value);

//                log.info("--------------hottestCampsite: " + redisDao.getValues("hottestCampsite")); // 제대로 저장되었는지 확인용

                return RepeatStatus.FINISHED;
            }
        };
    }
}
