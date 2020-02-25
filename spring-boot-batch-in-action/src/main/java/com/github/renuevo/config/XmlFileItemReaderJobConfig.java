package com.github.renuevo.config;

import com.github.renuevo.vo.CsvItemVo;
import com.github.renuevo.vo.XmlItemVo;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class XmlFileItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private static final int chunkSize = 2;

    @Bean
    public Job xmlFileItemReaderJob() {
        return jobBuilderFactory.get("xmlFileItemReaderJob")
                .start(xmlFileItemReaderStep())
                .build();
    }

    @Bean
    public Step xmlFileItemReaderStep() {
        return stepBuilderFactory.get("xmlFileItemReaderStep")
                .<XmlItemVo, XmlItemVo>chunk(chunkSize)
                .reader(xmlFileItemReader())
                .writer(x -> x.forEach(System.out::println))
                .build();
    }

    @Bean
    public StaxEventItemReader xmlFileItemReader() {
        return new StaxEventItemReaderBuilder<XmlItemVo>()
                .name("xmlFileItemReader")
                .resource(new ClassPathResource("/sample_xml_data.xml"))
                .addFragmentRootElements("item")
                .unmarshaller(itemMarshaller())
                .build();
    }

    @Bean
    public XStreamMarshaller itemMarshaller() {
        Map<String, Class<?>> aliases = Maps.newHashMap();
        aliases.put("items", XmlItemVo.class);
        aliases.put("number", Integer.class);
        aliases.put("item", String.class);
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);
        return xStreamMarshaller;
    }


}
