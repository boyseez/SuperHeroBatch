package it.esempio.superherobatch.mapper;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Data

@Entity
public class SuperHeroDTO {
    private String supereroe;
    private String nomeCompleto;
    private String editore;
    private String listaSuperpoteri;
    @Id
    private Long id;

    public SuperHeroDTO() {

    }

    public List<String> getListaPoteri(){
        return Arrays.asList(listaSuperpoteri.split(","));
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
