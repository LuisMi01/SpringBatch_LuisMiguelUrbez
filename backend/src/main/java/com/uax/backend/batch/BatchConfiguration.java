package com.uax.backend.batch;

import com.uax.backend.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private FlatFileItemReader<Transaction> reader;

    @Autowired
    private TransactionProcessor processor;

    @Autowired
    private MongoItemWriter<Transaction> writer;

    @Autowired
    private JobCompletionNotificationListener listener;

    @Bean
    public FlatFileItemReader<Transaction> reader() {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("transactions.csv"));
        reader.setLineMapper(new DefaultLineMapper<Transaction>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"accountId", "transactionType", "amount", "transactionDate"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {{
                setTargetType(Transaction.class);
            }});
        }});
        return reader;
    }

    @Bean
    public MongoItemWriter<Transaction> writer(MongoTemplate mongoTemplate) {
        MongoItemWriter<Transaction> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("processed_transactions");
        return writer;
    }

    @Bean
    public TransactionProcessor processor() {
        return new TransactionProcessor();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<Transaction> reader, TransactionProcessor processor, JdbcBatchItemWriter<Transaction> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Transaction, Transaction> chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}