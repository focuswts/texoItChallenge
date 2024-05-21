package com.texoit.worstmovie.config;

import com.texoit.worstmovie.batch.CsvProcessor;
import com.texoit.worstmovie.batch.CsvWriter;
import com.texoit.worstmovie.dto.CsvColumnsDTO;
import com.texoit.worstmovie.dto.CsvColumnsResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class BatchConfiguration {

    @Value("data/movielist.csv")
    private String fileInput;

    @Bean
    public FlatFileItemReader<CsvColumnsDTO> reader() {
        return new FlatFileItemReaderBuilder<CsvColumnsDTO>()
                .name("movieReader")
                .resource(new ClassPathResource(fileInput))
                .linesToSkip(1)
                .delimited()
                .delimiter(";")
                .names("year","title","studios","producers","winner")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(CsvColumnsDTO.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<CsvColumnsDTO, CsvColumnsResponseDTO> processor() {
        return new CsvProcessor();
    }

    @Bean
    public Job importMovies(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importMovies", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, CsvWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<CsvColumnsDTO, CsvColumnsResponseDTO> chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}
