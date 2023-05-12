package it.esempio.superherobatch.configuration;

import it.esempio.superherobatch.decider.DecessoDecider;
import it.esempio.superherobatch.model.Missione;
import it.esempio.superherobatch.reader.ReaderMissione;
import it.esempio.superherobatch.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;

@Configuration
@Slf4j
public class HeroBatchConfiguration {


    private static final String STEP_SALVA_DB = "Step_Salva_Su_DB_MySQL";
    private final static String NOME_JOB="Job Heroes Tracking";
    private static final String PLACEHOLDER = "{data}";
    private static final String FLOW_hero = "HeroFlow";
    private static final String INSERT_MISSION ="INSERT INTO elenco_missioni(nome_eroe,dettagli_missione,data_missione,decesso)VALUES(:nomeEroe,:dettMissione,:data,:mortoEroe);" ;
    private static final String STEP_SALVA_JSON ="Step_Salva_su_JSON" ;
    private static final String STEP_SALVA_CVS ="Step_Salva_su_CVS" ;
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

    @Value("${file.path.generati}")
    private String path_file_generati;



    //  JOB
    @Bean
    public Job jobHeroes(){
        return this.jobBuilderFactory
                .get(NOME_JOB)
                .start(decedutoFlow()).end()
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
    @JobScope
    public JobExecutionDecider decessoDecider(@Value("#{jobParameters['decesso']}") String decesso) {
        return new DecessoDecider(decesso);
    }


    // STEP
    @Bean
    public Flow decedutoFlow() {

        return new FlowBuilder<SimpleFlow>(FLOW_hero)
                .start(saveOnDbMySql())
                .next(decessoDecider(null)).on(DecessoDecider.DECEDUTO).to(saveOnCvs())
                .from(decessoDecider(null)).on(DecessoDecider.NESSUN_DECESSO).to(saveOnJson())
                .build();
    }



    @Bean
    public Step saveOnDbMySql(
    //ItemReader<Missione> leggiParametriReader
    ) {
        return stepBuilderFactory.get(STEP_SALVA_DB)
                .<Missione,Missione>chunk(chunk_size)
                .reader(leggiParametriReader(null,null, null,null))
                .writer(writeOnMySqlWriter())
                .build();
    }

    @Bean
    public Step saveOnJson(
            //ItemReader<Missione> leggiParametriReader
    ) {
        return stepBuilderFactory.get(STEP_SALVA_JSON)
                .<Missione,Missione>chunk(chunk_size)
                .reader(leggiParametriReader(null,null, null,null))
                .writer(writeJSONWriter())
                .build();
    }

    @Bean
    public Step saveOnCvs(
            //ItemReader<Missione> leggiParametriReader
    ) {
        return stepBuilderFactory.get(STEP_SALVA_CVS)
                .<Missione,Missione>chunk(chunk_size)
                .reader(leggiParametriReader(null,null, null,null))
                .writer(writeCVSWriter())
                .build();
    }



    // READER
    @Bean
    @StepScope
    public ItemReader<Missione> leggiParametriReader(@Value("#{jobParameters['nome_eroe']}") String hero,
                                                     @Value("#{jobParameters['dett_missione']}") String dett,
                                                     @Value("#{jobParameters['data']}") String data,
                                                     @Value("#{jobParameters['decesso']}") String decesso) {

        return new ReaderMissione(hero,dett,data,Boolean.valueOf(decesso));

    }

    // PROCESSING



    // WRITER
    @Bean
    public ItemWriter<Missione> writeOnMySqlWriter() {
        return new JdbcBatchItemWriterBuilder<Missione>()
                .dataSource(dataSource)
                .sql(INSERT_MISSION)
                .beanMapped()
                .build();
    }

    @Bean
    public ItemWriter<Missione> writeJSONWriter() {
        return new JsonFileItemWriterBuilder<Missione>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(path_file_generati+StringUtils.replace(this.nomeJsonMissione,PLACEHOLDER,Utils.getDataFormattata())))
                .name("Missioni")
                .lineSeparator(JsonFileItemWriter.DEFAULT_LINE_SEPARATOR)
                .encoding(JsonFileItemWriter.DEFAULT_CHARSET)
                .append(true)
                .build();
    }

    @Bean
    public ItemWriter<Missione> writeCVSWriter() {
        FlatFileItemWriter<Missione> itemWriter= new FlatFileItemWriter<>();
        String[] fieldName = {     "nomeEroe","dettMissione","data","mortoEroe"};

        itemWriter.setHeaderCallback(writer -> {
            StringBuilder stringBuilder= new StringBuilder();
            for (int i = 0; i < fieldName.length; i++) {
                stringBuilder.append(fieldName[i]);

                if( i < fieldName.length-1 ){
                    stringBuilder.append(',') ;
                }
            }
            writer.write(stringBuilder.toString());
        });
        FileSystemResource res = new FileSystemResource(path_file_generati+StringUtils.replace(this.nomeCvsDeceduti,PLACEHOLDER,Utils.getDataFormattata()));
        itemWriter.setResource(res);

        DelimitedLineAggregator<Missione> aggregator= new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");


        BeanWrapperFieldExtractor<Missione> extractor= new BeanWrapperFieldExtractor<>();

        extractor.setNames(fieldName);

        aggregator.setFieldExtractor(extractor);

        itemWriter.setLineAggregator(aggregator);
        itemWriter.setAppendAllowed(true);
        return itemWriter;

    }

}
