package com.ssafy.campinity.demo.batch.job;

import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.repository.message.MessageRepository;
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

@Configuration
@RequiredArgsConstructor
public class HardDeleteConfig {

    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepository jobRepository;
    private final CampinityDataSourceConfig campinityDataSourceConfig;
    private static Logger logger = LogManager.getLogger(HardDeleteConfig.class);
    private int chunkSize = 100;

    @PersistenceContext(unitName = "campinityEntityManager")
    private EntityManager em;
    private final MessageRepository messageRepository;


    @Bean
    public Job hardDeleteJob() {
        return jobBuilderFactory.get("hardDeleteJob")
                .preventRestart()
                .start(MessagehardDeleteStep())
                .next(MyCollectionHardDeleteStep())
                .build();
    }

    @Bean
    public Step MessagehardDeleteStep() {
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(campinityDataSourceConfig.campinityDataSource());
        tm.setEntityManagerFactory(em.getEntityManagerFactory());
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, tm);

        return stepBuilderFactory.get("MessagehardDeleteStep")
                .<Message, Message> chunk(chunkSize)
                .reader(MessageHardDeleteReader(null))
                .writer(this.MessageHardDeleteWrtier())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Message> MessageHardDeleteReader(@Value("#{jobParameters[time]}") String time) {
        logger.info("********** This is MessageHardDeleteReader");

        JpaPagingItemReader<Message> reader = new JpaPagingItemReader<Message>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT m FROM Message m WHERE m.expired = true");
        reader.setPageSize(chunkSize);
        reader.setName("MessageHardDeleteReader");
        reader.setEntityManagerFactory(em.getEntityManagerFactory());

        return reader;
    }

    public JpaItemCustomWriter<Message> MessageHardDeleteWrtier() {
        JpaItemCustomWriter<Message> jpaItemCustomWriter = new JpaItemCustomWriter<>();
        jpaItemCustomWriter.setEntityManagerFactory(em.getEntityManagerFactory());
        return jpaItemCustomWriter;
    }

    @Bean
    public Step MyCollectionHardDeleteStep() {
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(campinityDataSourceConfig.campinityDataSource());
        tm.setEntityManagerFactory(em.getEntityManagerFactory());
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, tm);

        return stepBuilderFactory.get("MyCollectionHardDeleteStep")
                .<MyCollection, MyCollection> chunk(chunkSize)
                .reader(MyCollectionHardDeleteReader(null))
                .writer(this.MyCollectionHardDeleteWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<MyCollection> MyCollectionHardDeleteReader(@Value("#{jobParameters[time]}") String time) {
        logger.info("********** This is MyCollectionHardDeleteReader");

        JpaPagingItemReader<MyCollection> reader = new JpaPagingItemReader<MyCollection>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT m FROM MyCollection m WHERE m.expired = true");
        reader.setPageSize(chunkSize);
        reader.setName("MyCollectionHardDeleteReader");
        reader.setEntityManagerFactory(em.getEntityManagerFactory());

        return reader;
    }

    public JpaItemCustomWriter<MyCollection> MyCollectionHardDeleteWriter() {
        JpaItemCustomWriter<MyCollection> jpaItemCustomWriter = new JpaItemCustomWriter<>();
        jpaItemCustomWriter.setEntityManagerFactory(em.getEntityManagerFactory());
        return jpaItemCustomWriter;
    }
}