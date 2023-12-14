package config;

import com.uax.backend.model.Usuario;
import com.uax.backend.reader.CustomMongoItemReader;
import com.uax.backend.service.UsuarioService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UsuarioService usuarioService;

    @Bean
    public Job myJob(Step myStep) {
        return new JobBuilder("myJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(myStep)
                .build();
    }

    @Bean
    public Step myStep(CustomMongoItemReader<Usuario> reader, ItemProcessor<Usuario, Usuario> processor, ItemWriter<Usuario> writer) {
        return new StepBuilder("myStep")
                .repository(jobRepository)
                .<Usuario, Usuario> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public CustomMongoItemReader<Usuario> reader() {
        return new CustomMongoItemReader<>(mongoTemplate, "usuarios", Usuario.class);
    }

    @Bean
    public ItemProcessor<Usuario, Usuario> processor() {
        // Implement your processor here.
        return null;
    }

    @Bean
    public ItemWriter<Usuario> writer() {
        // Implement your writer here.
        return null;
    }
}