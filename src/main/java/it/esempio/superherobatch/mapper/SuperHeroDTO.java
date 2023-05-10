package it.esempio.superherobatch.mapper;


import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class SuperHeroDTO {
    private String supereroe;
    private String nomeCompleto;
    private String editore;
    private String listaSuperpoteri;

    public List<String> getListaPoteri(){
        return Arrays.asList(listaSuperpoteri.split(","));
    }

    public static void main(String[] args) {
        SuperHeroDTO test = SuperHeroDTO.builder()
                .supereroe("Loki")
                .nomeCompleto("Loki Laufeyson")
                .editore("Marvel Comics")
                .listaSuperpoteri("Accelerated Healing,Durability,Energy Beams,Flight,Intelligence,Longevity,Magic,Omnilingualism,Portal Creation,Psionic Powers,Shapeshifting,Stamina,Super Strength,Toxin and Disease Resistance")
                .build();
        test.getListaPoteri()
                .stream().forEach(System.out::println);
    }
}
