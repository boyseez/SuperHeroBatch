package it.esempio.superherobatch.configuration;

import it.esempio.superherobatch.decider.DecessoDecider;
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
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;

@Configuration
@Slf4j
public class HeroBatchConfiguration {


    private static final String NOME_STEP_SALVA_DB = "Step_Salva_Su_DB_MySQL";
    private final static String NOME_JOB="Job Heroes Tracking";
    private static final String PLACEHOLDER = "{data}";
    private static final String FLOW_NAME = "HeroFlow";

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
                .start(flowHeroes())
                .end()
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
                        jobExecution.getJobParameters().getParameters().keySet().forEach(item-> log.debug("Chiave ="+item + ", Valore: "+jobExecution.getJobParameters().getParameters().get(item))
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


    @Bean
    public JobExecutionDecider decessoDecider(@Value("#{jobParameters['decesso']}") String decesso) {
        return new DecessoDecider(decesso);
    }


    //TODO STEP
    //***********************************  STEP  **********************************************
    @Bean
    public Flow flowHeroes() {

        // TODO implementare flussi paralleli tra e' morto qualcuno ed e' un
        // TODO super eroe marvel
        return new FlowBuilder<SimpleFlow>(FLOW_NAME)
                .start(saveOnDbMySql())
                .on("*")
                    .to(decessoDecider(null)).on(DecessoDecider.DECEDUTO).to(esportaFileCVSDeceduti())
                    .from(decessoDecider(null)).on(DecessoDecider.NESSUN_DECESSO).to(esportaFileJsonMissione())
                .build();
    }

    @Bean
    public Step esportaFileJsonMissione() {
        //TODO implementare exp JsonMIssione
        return null;
    }

    @Bean
    public Step esportaFileCVSDeceduti() {
        //TODO implementare exp esportaFileCVSDeceduti
        return null;
    }



    @Bean
    public Step saveOnDbMySql() {
        return stepBuilderFactory.get(NOME_STEP_SALVA_DB)
                .<Missione,Missione>chunk(chunk_size)
                .reader(leggiParametriReader(null,null, null,null))
                .writer(items -> {
                    log.debug("==================WRITER==================");
                    for (int i = 0; i < items.size(); i++) {
                        log.debug(">>>>Elemento "+i+": "+items);
                    }
                })
                .build();
    }




    //TODO READER
    //************************************  READER  ********************************************
    @Bean
    @StepScope
    public ItemReader<Missione> leggiParametriReader(@Value("#{jobParameters['nome_eroe']}") String hero,
                                                     @Value("#{jobParameters['dett_missione']}") String dett,
                                                     @Value("#{jobParameters['data']}") String data,
                                                     @Value("#{jobParameters['decesso']}") String decesso) {

        return new ReaderMissione(hero,dett,data,Boolean.valueOf(decesso));

    }

    //TODO PROCESSING
    //************************************  PROCESSING  ****************************************



    //TODO WRITER
    //************************************  WRITER  ********************************************




}
