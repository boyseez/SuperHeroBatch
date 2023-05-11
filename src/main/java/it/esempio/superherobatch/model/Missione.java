package it.esempio.superherobatch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Missione {
    private String nomeEroe;
    private String dettMIssione;

}
