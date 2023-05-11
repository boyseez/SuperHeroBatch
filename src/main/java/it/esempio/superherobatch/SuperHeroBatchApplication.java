package it.esempio.superherobatch;

import it.esempio.superherobatch.configuration.HeroBatchConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@Slf4j
@EnableBatchProcessing
public class SuperHeroBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperHeroBatchApplication.class, args);
    }

    @Autowired
    public JobLauncher jobLauncher;


}
