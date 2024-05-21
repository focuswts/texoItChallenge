package com.texoit.worstmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job importMovies;

    @PostMapping("/csvdataUpload")
    public String saveCsvData() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputFile", "")
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(importMovies, jobParameters);

        while (jobExecution.isRunning()) {
            log.info("..................");
        }

        return jobExecution.getStatus().toString();
    }

}
