package it.esempio.superherobatch;

import it.esempio.superherobatch.configuration.HeroBatchConfiguration;
import it.esempio.superherobatch.model.Missione;
import it.esempio.superherobatch.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableBatchProcessing
public class SuperHeroBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperHeroBatchApplication.class, args);
    }

    @Autowired
    public JobLauncher jobLauncher;

    @Autowired
    public Job job;

    @Scheduled(fixedRate = 5000, initialDelayString = "#{ T(java.util.concurrent.ThreadLocalRandom).current().nextInt(1000) }")
    public void runJob() throws Exception {
        JobParametersBuilder parametersBuilder=new JobParametersBuilder();

        Missione m = Utils.generatoreDiMissione(1).get(0);
        parametersBuilder.addString(Missione.KEY_NOME_EROE,m.getNomeEroe());
        parametersBuilder.addString(Missione.KEY_DETT_MISSIONE,m.getDettMissione());
        parametersBuilder.addDate(Missione.KEY_DATA, new Date());

        this.jobLauncher.run(job,parametersBuilder.toJobParameters());

    }

}
