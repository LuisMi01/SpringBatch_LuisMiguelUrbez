package com.uax.backend.batch;

import com.uax.backend.model.Usuario;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobRepository jobRepository;

    @Autowired
    public PlatformTransactionManager transactionManager;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Bean
    public FlatFileItemReader<Usuario> reader() {
        FlatFileItemReader<Usuario> reader = new FlatFileItemReader<Usuario>();
        reader.setResource(new ClassPathResource("MOCK_DATA.csv"));
        reader.setLineMapper(new DefaultLineMapper<Usuario>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                //los atributos de la clase usuario
                setNames(new String[]{"id", "nombre", "email", "password", "saldo", "cuenta", "movimientos"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Usuario>() {{
                setTargetType(Usuario.class);
            }});
        }});
        return reader;
    }

    @Bean
    public UsuarioItemProcessor processor() {
        return new UsuarioItemProcessor();
    }

    @Bean
    public MongoItemWriter<Usuario> writer() {
        MongoItemWriter<Usuario> writer = new MongoItemWriter<Usuario>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("banco");
        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener){

        //crear el job builder para versiones anteriores a la 5.0
        JobBuilder jobBuilder = new JobBuilder("importUserJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
               .listener(listener);

        return jobBuilder
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<Usuario, Usuario>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
