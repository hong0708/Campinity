package com.ssafy.campinity.demo.batch.job;


import com.ssafy.campinity.core.entity.fcm.FcmMessage;
import com.ssafy.campinity.core.repository.fcm.FcmMessageRepository;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import com.ssafy.campinity.demo.batch.writer.JpaItemCustomWriter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class FcmMessageDeleteConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepository jobRepository;
    private final CampinityDataSourceConfig campinityDataSourceConfig;
    private final Logger logger = LogManager.getLogger(FcmMessageDeleteConfig.class);
    private int chunkSize = 100;

    @PersistenceContext(unitName = "campinityEntityManager")
    private EntityManager em;
    private final FcmMessageRepository fcmMessageRepository;

    @Bean
    public Job FcmMessageDeleteJob(){
        return jobBuilderFactory.get("FcmMessageDeleteJob")
                .preventRestart()
                .start(FcmMeesageDeleteStep())
                .build();
    }

    @Bean
    @StepScope
    public Step FcmMeesageDeleteStep(){
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(campinityDataSourceConfig.campinityDataSource());
        tm.setEntityManagerFactory(em.getEntityManagerFactory());
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, tm);

        return stepBuilderFactory.get("FcmMessageDeleteStep")
                .<FcmMessage, FcmMessage>chunk(chunkSize)
                .reader(FcmMessageDeleteReader(null))
                .writer(this.FcmMessageDeleteWriter())
                .build();

    }

    private JpaPagingItemReader<FcmMessage> FcmMessageDeleteReader(@Value("#{jobParameter[time]}") String time) {
        logger.info("********** This is FcmTokenDeleteReader************");

        JpaPagingItemReader<FcmMessage> reader = new JpaPagingItemReader<>(){
            @Override
            public int getPage(){
                return 0;
            }
        };

        String query = "SELECT fm FROM FcmMessage fm WHERE fm.expired = true";

        reader.setQueryString(query);
        reader.setPageSize(chunkSize);
        reader.setName("FcmMessageDeleteReader");
        reader.setEntityManagerFactory(em.getEntityManagerFactory());

        return reader;
    }

    private JpaItemCustomWriter<FcmMessage> FcmMessageDeleteWriter() {
        JpaItemCustomWriter<FcmMessage> jpaItemCustomWriter = new JpaItemCustomWriter<>();
        jpaItemCustomWriter.setEntityManagerFactory(em.getEntityManagerFactory());
        return jpaItemCustomWriter;
    }
}