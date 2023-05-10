package it.esempio.superherobatch;

import it.esempio.superherobatch.mapper.SuperHeroDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SuperHeroBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperHeroBatchApplication.class, args);
    }

    
  /*  @Bean
    public CommandLineRunner demo(SpringApplication repo) {
        return (args) -> {
            for (int i = 0; i < 100; i++) {
                log.info("******************************************");
            }
        };
    }*/

}
