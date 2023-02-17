package com.ssafy.campinity.demo.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.core.dto.CampsiteRankingResDTO;
import com.ssafy.campinity.core.repository.redis.RedisDao;
import com.ssafy.campinity.demo.batch.campinityRepository.CampinityCampsiteCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class HighestScoredCampsiteConfig {

    private final RedisDao redisDao;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CampinityCampsiteCustomRepository campinityCampsiteCustomRepository;
    private static Logger logger = LogManager.getLogger(HighestScoredCampsiteConfig.class);

    @Bean
    public Job HighestScoredCampsiteJob() {
        logger.info("********** This is HighestScoredCampsiteJob");

        return jobBuilderFactory.get("HighestScoredCampsiteJob")
                .preventRestart()
                .start(HighestScoredCampsiteStep())
                .build();
    }

    public Step HighestScoredCampsiteStep() {
        logger.info("********** This is HighestScoredCampsiteStep");
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

                List<CampsiteRankingResDTO> campsiteHighestRankingData =
                        campinityCampsiteCustomRepository.findCampsiteHighestRankingData(start, end);

                ObjectMapper mapper = new ObjectMapper();
                String value = mapper.writeValueAsString(campsiteHighestRankingData);
                redisDao.setValues("highestCampsite", value);

                logger.info("-------------------highestCampsite" + redisDao.getValues("highestCampsite"));

                return RepeatStatus.FINISHED;
            }
        };
    }
}
