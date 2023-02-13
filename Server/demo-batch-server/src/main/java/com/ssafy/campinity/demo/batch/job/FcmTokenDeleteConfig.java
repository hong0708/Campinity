package com.ssafy.campinity.demo.batch.job;

import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class FcmTokenDeleteConfig {

    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory; // db read, processor, writer
    private final JobRepository jobRepository; // job execution info
    private final CampinityDataSourceConfig campinityDataSourceConfig; // multi db에서 core db를 가지고 오는 config
    private static Logger logger = LogManager.getLogger(FcmTokenDeleteConfig.class);
    private int chunkSize = 100;

    @PersistenceContext(unitName = "campinityEntityManager") // 특정 entity manager를 가져올 때 사용하는 annotation
    private EntityManager em;
    private final FcmTokenRepository fcmTokenRepository;


    @Bean
    public Job fcmTokenDeleteJob() {
        return jobBuilderFactory.get("fcmTokenDeleteJob")
                .preventRestart()
                .start(fcmTokenDeleteStep())
                .build();
    }

    @Bean
    public Step fcmTokenDeleteStep(){
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(campinityDataSourceConfig.campinityDataSource());
        tm.setEntityManagerFactory(em.getEntityManagerFactory());
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, tm);

        return stepBuilderFactory.get("fcmTokenDeleteStep")
                .<FcmToken, FcmToken>chunk(chunkSize)
                .reader(FcmTokenDeleteReader(null))
                .writer(this.FcmTokenDeleteWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<FcmToken> FcmTokenDeleteReader(@Value("#{jobParameters[time]}") String time){
        logger.info("********** This is FcmTokenDeleteReader");

        JpaPagingItemReader<FcmToken> reader = new JpaPagingItemReader<FcmToken>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        LocalDate today = LocalDate.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("today", today);

        reader.setQueryString("SELECT t FROM FcmToken t WHERE t.expiredDate < :today");
        reader.setPageSize(chunkSize);
        reader.setName("FcmTokenDeleteReader");
        reader.setEntityManagerFactory(em.getEntityManagerFactory());
        reader.setParameterValues(parameters);

        return reader;
    }

    public JpaItemCustomWriter<FcmToken> FcmTokenDeleteWriter() {
        JpaItemCustomWriter<FcmToken> jpaItemCustomWriter = new JpaItemCustomWriter<>();
        jpaItemCustomWriter.setEntityManagerFactory(em.getEntityManagerFactory());
        return jpaItemCustomWriter;
    }

}