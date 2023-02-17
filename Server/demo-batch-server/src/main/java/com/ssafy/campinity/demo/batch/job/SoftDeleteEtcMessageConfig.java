package com.ssafy.campinity.demo.batch.job;

import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.utils.ImageUtil;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class SoftDeleteEtcMessageConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final ImageUtil imageUtil;
    private final JobRepository jobRepository;
    private final CampinityDataSourceConfig campinityDataSourceConfig;
    private static Logger logger = LogManager.getLogger(SoftDeleteEtcMessageConfig.class);


    @PersistenceContext(unitName = "campinityEntityManager")
    private EntityManager em;
    private final int chunkSize = 100;


    @Bean
    public Job softDeleteEtcMessageJob() {
        logger.info("********** This is softDeleteEtcMessageJob");
        return jobBuilderFactory.get("softDeleteEtcMessageJob")
                .preventRestart()
                .start(softDeleteEtcMessageStep())
                .build();
    }

    @Bean
    public Step softDeleteEtcMessageStep() {
        logger.info("********** This is softDeleteEtcMessageStep");
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(campinityDataSourceConfig.campinityDataSource());
        tm.setEntityManagerFactory(em.getEntityManagerFactory());
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, tm);

        return stepBuilderFactory.get("softDeleteEtcMessageStep")
                .<Message, Message> chunk(chunkSize)
                .reader(softDeleteEtcMessageReader(null))
                .processor(this.softDeleteEtcMessageProcessor())
                .writer(this.softDeleteEtcMessageWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Message> softDeleteEtcMessageReader(@Value("#{jobParameters[time]}") String time) {
        logger.info("********** This is softDeleteEtcMessageReader");

        // 목요일 새벽 4시에 월요일 23시 59분 59초 9999 이전에 작성된 ETC 메세지는 삭제
        LocalDate today = LocalDate.now();  // test용: of(2023, 1, 29)
        LocalDate targetDay = today.minusDays(2);
        LocalDateTime standard = LocalDateTime.of(targetDay, LocalTime.of(0, 0, 0));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("standard", standard);

        JpaPagingItemReader<Message> reader = new JpaPagingItemReader<Message>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT m FROM Message m WHERE m.messageCategory = 'ETC' AND m.createdAt < :standard AND expired = FALSE");
        reader.setPageSize(chunkSize);
        reader.setName("softDeleteEtcMessageReader");
        reader.setEntityManagerFactory(em.getEntityManagerFactory());
        reader.setParameterValues(parameters);

        return reader;
    }

    public ItemProcessor<Message, Message> softDeleteEtcMessageProcessor() {
        return new ItemProcessor<Message, Message>() {
            @Override
            public Message process(@NotNull Message item) throws Exception {
                logger.info("********** This is softDeleteEtcMessageProcessor");
                String imagePath = item.getImagePath();
                if (!imagePath.isEmpty()){
                    try {
                        imageUtil.removeImage(imagePath);
                        item.deleteMessageImage();
                    }
                    catch (SecurityException e) {throw new SecurityException(e.getMessage());}
                    catch (NullPointerException e) {throw new NullPointerException(e.getMessage());}
                }
                item.softDeleteEtcMessage();
                return item;
            }
        };
    }

    public JpaItemWriter<Message> softDeleteEtcMessageWriter() {
        logger.info("********** This is softDeleteEtcMessageWriter");
        JpaItemWriter<Message> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(em.getEntityManagerFactory());
        return jpaItemWriter;
    }
}
