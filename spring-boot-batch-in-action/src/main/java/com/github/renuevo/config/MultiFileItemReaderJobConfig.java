package com.github.renuevo.config;


import com.github.renuevo.vo.ItemVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 * <pre>
 * @className : MultiFileItemReaderJobConfig
 * @author : Deokhwa.Kim
 * @since : 2020-03-04
 * </pre>
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class MultiFileItemReaderJobConfig {

    private final ResourceLoader resourceLoader;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int chunkSize = 2;

    @Bean
    public Job multiTxtFileItemReaderJob() {
        return jobBuilderFactory.get("multiTxtFileItemReaderJob")
                .start(multiTxtFileItemReaderStep())
                .build();
    }

    @Bean
    public Step multiTxtFileItemReaderStep() {
        return stepBuilderFactory.get("multiTxtFileItemReaderStep")
                .<ItemVo, ItemVo>chunk(chunkSize)
                .reader(multiResourceItemReader())
                .writer(itemVo -> itemVo.stream()
                        .map(ItemVo::toString)
                        .forEach(log::info))
                .build();
    }

    @Bean
    @SneakyThrows
    public MultiResourceItemReader<ItemVo> multiResourceItemReader(){
        MultiResourceItemReader<ItemVo> resourceItemReader = new MultiResourceItemReader<ItemVo>();
        resourceItemReader.setResources(ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResources("read_sample/*.txt"));
        resourceItemReader.setDelegate(multiFileItemReader());
        return resourceItemReader;
    }


    @Bean
    public FlatFileItemReader<ItemVo> multiFileItemReader() {
        FlatFileItemReader<ItemVo> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLineMapper((line, lineNumber) -> new ItemVo(line));
        return flatFileItemReader;
    }


}
