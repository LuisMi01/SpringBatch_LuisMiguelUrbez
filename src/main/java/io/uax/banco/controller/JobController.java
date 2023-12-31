package io.uax.banco.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @GetMapping
    public String jobs() {
        return "import/jobs";
    }

    /*
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String uploadedFileName = file.getOriginalFilename();
        try {
            Path path = Paths.get("src/main/resources/" + uploadedFileName);
            Files.write(path, file.getBytes());

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", uploadedFileName)
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);

            //Files.delete(path);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/importCustomers";
    }*/

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String uploadedFileName = file.getOriginalFilename();
        try {
            Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), uploadedFileName);
            Files.write(tempPath, file.getBytes());

            Path resourcesPath = Paths.get("src/main/resources", uploadedFileName);
            Files.copy(tempPath, resourcesPath);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", uploadedFileName)
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);

            Files.delete(tempPath);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/importCustomers";
    }
}