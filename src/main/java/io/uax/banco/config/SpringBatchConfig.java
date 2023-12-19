package io.uax.banco.config;


import io.uax.banco.domain.Usuario;
import io.uax.banco.processor.UsuarioProcessor;
import io.uax.banco.repos.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    private UsuarioRepository usuarioRepository;


    @Bean
    public FlatFileItemReader<Usuario> reader() {
        FlatFileItemReader<Usuario> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/transacctions.csv"));
        itemReader.setName("lectorCSV");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

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

   /* @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() throws SchedulerException {
        if (scheduler.isInStandbyMode()) {
            scheduler.start();
        }

        try {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.anyJobGroup())) {
                System.out.println("Job: " + jobKey.getName());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }*/

    @Bean
    public UsuarioProcessor processor() {
        return new UsuarioProcessor();
    }

    @Bean
    public RepositoryItemWriter<Usuario> writer() {
        RepositoryItemWriter<Usuario> writer = new RepositoryItemWriter<>();
        writer.setRepository(usuarioRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                .<Usuario, Usuario>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("Usuario", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
