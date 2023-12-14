package com.uax.backend.batch;

import com.uax.backend.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log =
            LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public JobCompletionNotificationListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Fin del job. Resultado para verificación:");

            List<Transaction> results = mongoTemplate.findAll(Transaction.class, "banco");

            for (Transaction transaction : results) {
                log.info("[" + transaction + "] en base.");
            }

        }
    }
}