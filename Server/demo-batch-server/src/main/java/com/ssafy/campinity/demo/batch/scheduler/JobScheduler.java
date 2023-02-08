package com.ssafy.campinity.demo.batch.scheduler;

import com.ssafy.campinity.demo.batch.job.HardDeleteConfig;
import com.ssafy.campinity.demo.batch.job.HighestScoredCampsiteConfig;
import com.ssafy.campinity.demo.batch.job.HottestCampsiteConfig;
import com.ssafy.campinity.demo.batch.job.SoftDeleteEtcMessageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final SoftDeleteEtcMessageConfig softDeleteEtcMessageConfig;
    private final HottestCampsiteConfig hottestCampsiteConfig;
    private final HighestScoredCampsiteConfig highestScoredCampsiteConfig;
    private final HardDeleteConfig hardDeleteConfig;
    private static Logger logger = LogManager.getLogger(JobScheduler.class);

    @PostConstruct
    public void run() {
        Map<String, JobParameter> jobParameterMap1 = new HashMap<>();
        jobParameterMap1.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters1 = new JobParameters(jobParameterMap1);

        try {
            jobLauncher.run(hottestCampsiteConfig.HottestCampsiteJob(), jobParameters1);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }

        Map<String, JobParameter> jobParameterMap2 = new HashMap<>();
        jobParameterMap2.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters2 = new JobParameters(jobParameterMap2);

        try {
            jobLauncher.run(highestScoredCampsiteConfig.HighestScoredCampsiteJob(), jobParameters2);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 4 * * *") // 0 0 4 * * *(초, 분, 시, 일, 월, 요일)
    public void runSoftDeleteEtcMessage() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {

            jobLauncher.run(softDeleteEtcMessageConfig.softDeleteEtcMessageJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
             org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 5 4 * * 2") // 매주 월요일 오전 4시 5분에 실행 0 5 4 * * 2
    public void runHottestCampsite() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            jobLauncher.run(hottestCampsiteConfig.HottestCampsiteJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 10 4 * * 2") // 매주 월요일 오전 4시 10분에 실행 0 10 4 * * 2
    public void runHighestCampsite() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            jobLauncher.run(highestScoredCampsiteConfig.HighestScoredCampsiteJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 20 4 * * 2") // 매주 월요일 오전 4시 10분에 실행 0 10 4 * * 2
    public void runHardDelete() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            jobLauncher.run(hardDeleteConfig.hardDeleteJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }
    }
}
