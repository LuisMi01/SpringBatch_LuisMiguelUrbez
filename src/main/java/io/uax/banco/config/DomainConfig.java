package io.uax.banco.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.uax.banco.domain")
@EnableJpaRepositories("io.uax.banco.repos")
@EnableTransactionManagement
public class DomainConfig {
}
