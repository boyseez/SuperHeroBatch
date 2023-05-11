package it.esempio.superherobatch.model;

import lombok.Builder;
import lombok.*;

import java.util.Objects;

@Data
@Builder
public class Missione {
    public static final String KEY_NOME_EROE="nome_eroe";
    public static final String KEY_DETT_MISSIONE="dett_missione";
    public static final String KEY_DATA="data";

    private String nomeEroe;
    private String dettMissione;
    private String data;


}
