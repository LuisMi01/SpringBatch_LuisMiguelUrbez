package io.uax.banco.components;

import lombok.Setter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Setter
@Component
public class FileNameStepExecutionListener {

    private String fileName;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().putString("fileName", fileName);
    }
}