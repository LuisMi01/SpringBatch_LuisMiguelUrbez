package io.uax.banco.config;


import io.uax.banco.domain.Usuario;
import io.uax.banco.processor.UsuarioProcessor;
import io.uax.banco.repos.UsuarioRepository;

import lombok.AllArgsConstructor;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private UsuarioRepository usuarioRepository;
    private DataSource dataSource;

    @Bean
    @StepScope
    public FlatFileItemReader<Usuario> reader() {
        FlatFileItemReader<Usuario> reader = new FlatFileItemReader<Usuario>();
        reader.setResource(new FileSystemResource("src/main/resources/Banco.csv"));

        reader.setLineMapper(new DefaultLineMapper<Usuario>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "account_id", "amount",  "transaction_type", "transaction_date"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Usuario>() {{
                setTargetType(Usuario.class);
            }});
        }});
        return reader;
    }

  /*  @Bean
    @StepScope
    public FlatFileItemReader<Usuario> reader() {
        FlatFileItemReader<Usuario> reader = new FlatFileItemReader<Usuario>();
        reader.setResource(new ClassPathResource("Banco.csv"));
        reader.setLineMapper(new DefaultLineMapper<Usuario>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "account_id", "amount",  "transaction_type", "transaction_date"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Usuario>() {{
                setTargetType(Usuario.class);
            }});
        }});
        return reader;
    }
*/

    private LineMapper<Usuario> lineMapper() {
        DefaultLineMapper<Usuario> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "account_id", "amount",  "transaction_type", "transaction_date");

        BeanWrapperFieldSetMapper<Usuario> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Usuario.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public UsuarioProcessor processor() {
        return new UsuarioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Usuario> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Usuario> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO usuarios (id, account_id, amount, transaction_type, transaction_date) VALUES (:id, :accountId, :amount, :transactionType, :transactionDate)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job job() {
        return new JobBuilder("Usuario", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                .<Usuario, Usuario>chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer(dataSource))
                .taskExecutor(taskExecutor())
                .build();
    }



    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(200);
        taskExecutor.setMaxPoolSize(200);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
