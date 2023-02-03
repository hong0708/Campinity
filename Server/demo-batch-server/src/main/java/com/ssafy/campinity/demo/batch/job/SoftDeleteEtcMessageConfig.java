package com.ssafy.campinity.demo.batch.job;

import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.entity.message.MessageCategory;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SoftDeleteEtcMessageConfig {

    private final MessageRepository messageRepository;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job softDeleteEtcMessageJob() {
        log.info("********** This is softDeleteEtcMessageJob");
        return jobBuilderFactory.get("softDeleteEtcMessageJob")
                .preventRestart()
                .start(softDeleteEtcMessageStep())
                .build();
    }

    @Bean
    public Step softDeleteEtcMessageStep() {
        log.info("********** This is softDeleteEtcMessageStep");
        return stepBuilderFactory.get("softDeleteEtcMessageStep")
                .<Message, Message> chunk(100)
                .reader(softDeleteEtcMessageReader(null))
                .processor(this.softDeleteEtcMessageProcessor())
                .writer(this.softDeleteEtcMessageWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<Message> softDeleteEtcMessageReader(@Value("#{jobParameters[time]}") String time) {
        log.info("********** This is softDeleteEtcMessageReader");

        // 목요일 새벽 4시에 월요일 23시 59분 59초 9999 이전에 작성된 ETC 메세지는 삭제
        LocalDate today = LocalDate.now();  // test용: of(2023, 1, 29)
        LocalDate targetDay = today.minusDays(2);
        LocalDateTime standard = LocalDateTime.of(targetDay, LocalTime.of(0, 0, 0));

        List<Message> expiredMessages = messageRepository.findByMessageCategoryAndCreatedAtBeforeAndExpiredIsFalse(MessageCategory.ETC, standard);

        log.info("          - start time : " + targetDay);
        log.info("          - targetMessage SIZE : " + expiredMessages.size());

        return new ListItemReader<>(expiredMessages);
    }

    public ItemProcessor<Message, Message> softDeleteEtcMessageProcessor() {
        return new ItemProcessor<Message, Message>() {
            @Override
            public Message process(@NotNull Message item) throws Exception {
                log.info("********** This is softDeleteEtcMessageProcessor");
                return item.softDeleteEtcMessage();
            }
        };
    }

    public ItemWriter<Message>  softDeleteEtcMessageWriter() {
        log.info("********** This is softDeleteEtcMessageWriter");
        return ((List<? extends Message> expiredMessages) -> messageRepository.saveAll(expiredMessages));
    }
}
