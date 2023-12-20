package io.uax.banco.components;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FileNameJobExecutionListener implements JobExecutionListener {

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().putString("fileName", fileName);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Trabajo terminado");
    }
}