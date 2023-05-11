package it.esempio.superherobatch.configuration;

import it.esempio.superherobatch.model.Missione;
import it.esempio.superherobatch.reader.ReaderMissione;
import it.esempio.superherobatch.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;

@Configuration
@Slf4j
public class HeroBatchConfiguration {


    private static final String NOME_STEP_SALVA_DB = "Step_Salva_Su_DB_MySQL";
    private final static String NOME_JOB="Job Heroes Tracking";
    private static final String PLACEHOLDER = "{data}";

    @Value("${chunk.size}")
    private Integer chunk_size;

    @Value("${file.nome.csv.deceduti}")
    private String nomeCvsDeceduti;

    @Value("${file.nome.csv.rinascite}")
    private String nomeCvsRinascite;

    @Value("${file.nome.json}")
    private String nomeJsonMissione;

    @Value("${file.intestazione.csv}")
    private String[] intestazioneCvs;


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;


    //TODO JOB
    //************************************  JOB  ***********************************************
    @Bean
    public Job jobHeroes(){
        return this.jobBuilderFactory
                .get(NOME_JOB)
                .start(stepInit())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        String data =Utils.getDataFormattata(Objects.requireNonNull(jobExecution
                                            .getJobParameters()
                                            .getDate(Missione.KEY_DATA))
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime());

                        nomeCvsDeceduti = StringUtils.replace(nomeCvsDeceduti,PLACEHOLDER,data);
                        nomeCvsRinascite = StringUtils.replace(nomeCvsRinascite,PLACEHOLDER,data);
                        nomeJsonMissione = StringUtils.replace(nomeJsonMissione,PLACEHOLDER,data);

                        log.debug("------------------------------------------------------------------------------------");
                        log.debug("|                              PARAMETRI PROGETTO                                  |");
                        log.debug("------------------------------------------------------------------------------------");
                        log.debug("chunk_size :" +chunk_size);
                        log.debug("nomeCvsDeceduti: "+nomeCvsDeceduti);
                        log.debug("nomeCvsRinascite: "+nomeCvsRinascite);
                        log.debug("nomeJsonMissione: "+nomeJsonMissione);
                        log.debug("intestazioneCvs: "+ Arrays.toString(intestazioneCvs));
                        log.debug("------------------------------------------------------------------------------------");
                        log.debug("|                                 PARAMETRI JOB                                    |");
                        log.debug("------------------------------------------------------------------------------------");
                        log.debug("Lista Parametri ");
                        jobExecution.getJobParameters().getParameters().keySet().forEach(item->{
                            log.debug("Chiave ="+item + ", Valore: "+jobExecution.getJobParameters().getParameters().get(item));
                        }
                        );
                        log.debug("------------------------------------------------------------------------------------");
                        log.debug("------------------------------------------------------------------------------------");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.debug("*******************************     FINE JOB     ************************************");
                    }
                })
                .build();
    }



    //TODO STEP
    //***********************************  STEP  **********************************************
    @Bean
    public Step stepInit(){
        return this.stepBuilderFactory
                .get(NOME_STEP_SALVA_DB)
                .<Missione,Missione>chunk(chunk_size)
                .reader( readerMissione() )
                .writer( i  -> {
                    log.info("Elemento: "+i);
                })
                .build();
    }



    //TODO READER
    //************************************  READER  ********************************************
    private ItemReader<Missione> readerMissione() {
        return new ReaderMissione();
    }


    //TODO PROCESSING
    //************************************  PROCESSING  ****************************************



    //TODO WRITER
    //************************************  WRITER  ********************************************




}
