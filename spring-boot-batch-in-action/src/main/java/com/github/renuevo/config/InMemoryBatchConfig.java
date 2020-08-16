package com.github.renuevo.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

/**
 * <pre>
 * @className : JobConfig
 * @author : Deokhwa.Kim
 * @since : 2020-08-13
 * </pre>
 */
//@Configuration
public class InMemoryBatchConfig implements BatchConfigurer {

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;

   // @PostConstruct
    public void init() throws Exception {
        if (this.transactionManager == null) this.transactionManager = new ResourcelessTransactionManager();

        MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(this.transactionManager);
        MapJobExplorerFactoryBean mapJobExplorerFactoryBean = new MapJobExplorerFactoryBean(mapJobRepositoryFactoryBean);

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        this.jobRepository = mapJobRepositoryFactoryBean.getObject();
        assert this.jobRepository != null;
        jobLauncher.setJobRepository(this.jobRepository);

        this.jobExplorer = mapJobExplorerFactoryBean.getObject();

        mapJobExplorerFactoryBean.afterPropertiesSet();
        jobLauncher.afterPropertiesSet();
        this.jobLauncher = jobLauncher;

    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        return this.jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return this.transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return this.jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return this.jobExplorer;
    }
}
