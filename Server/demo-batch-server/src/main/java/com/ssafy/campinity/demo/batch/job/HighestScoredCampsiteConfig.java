package com.ssafy.campinity.demo.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.core.dto.CampsiteHighestRankingReqDTO;
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
public class HighestScoredCampsiteConfig {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CampinityCampsiteCustomRepository campinityCampsiteCustomRepository;

    @Bean
    public Job HighestScoredCampsiteJob() {
        log.info("********** This is HighestScoredCampsiteJob");

        return jobBuilderFactory.get("HighestScoredCampsiteJob")
                .preventRestart()
                .start(HighestScoredCampsiteStep())
                .build();
    }

    public Step HighestScoredCampsiteStep() {
        log.info("********** This is HighestScoredCampsiteStep");
        return stepBuilderFactory.get("HighestScoredCampsiteStep")
                .tasklet(HighestScoredCampsiteTasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet HighestScoredCampsiteTasklet(@Value("#{jobParameters[time]}") String time) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                LocalDate today = LocalDate.now();
                LocalDate start = today.minusDays(7);
                LocalDate end = today.minusDays(1);

                List<CampsiteHighestRankingReqDTO> campsiteHighestRankingData =
                        campinityCampsiteCustomRepository.findCampsiteHighestRankingData(start, end);

                Collections.sort(campsiteHighestRankingData, new Comparator<CampsiteHighestRankingReqDTO>() {
                    @Override
                    public int compare(CampsiteHighestRankingReqDTO o1, CampsiteHighestRankingReqDTO o2) {
                        if (o2.getTotalRate() - o1.getTotalRate() == 0.0) {
                            return 0;
                        } else if (o2.getTotalRate() - o1.getTotalRate() > 0.0){
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                List<CampsiteRankingResDTO> result = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    CampsiteHighestRankingReqDTO camp = campsiteHighestRankingData.get(i);
                    result.add(CampsiteRankingResDTO.builder().campsiteId(camp.getCampsiteId()).ranking(i + 1)
                            .doName(camp.getDoName()).sigunguName(camp.getSigunguName()).campName(camp.getCampName())
                            .firstImageUrl(camp.getFirstImageUrl()).build());

                    log.info(camp.getCampsiteId() + " : " + camp.getTotalRate());
                }

                ObjectMapper mapper = new ObjectMapper();
                String value = mapper.writeValueAsString(result);
                redisDao.setValues("highestCampsite", value);

//                log.info("-------------------highestCampsite" + redisDao.getValues("highestCampsite"));

                return RepeatStatus.FINISHED;
            }
        };
    }
}
