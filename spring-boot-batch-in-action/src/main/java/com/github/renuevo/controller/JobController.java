package com.github.renuevo.controller;

/* web dependencies 주석으로 인한 주석처리
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public JobController(JobLauncher jobLauncher, @Qualifier("JobParameterBean") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/launchjob")
    public String handle(@RequestParam("requestDate") String requestDate) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("requestDate", requestDate)
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage(), e);
        }
        return "Deon";
    }
}
*/