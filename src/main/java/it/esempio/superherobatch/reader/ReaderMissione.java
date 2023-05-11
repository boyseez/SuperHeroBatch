package it.esempio.superherobatch.reader;

import it.esempio.superherobatch.model.Missione;
import it.esempio.superherobatch.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;

@Slf4j
public class ReaderMissione implements ItemReader<Missione> {
    private Missione s = null;

    private String her;
    private String dett;
    private String data;

    public ReaderMissione(String hero, String dett, String data) {
        this.her = hero;
        this.dett = dett;
        this.data = data;
    }

    @Override
    public Missione read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Missione mission = Missione.builder()
                .nomeEroe(her)
                .dettMissione(dett)
                .data(data)
                .build();
        if (s!=null && s.equals(mission)) {
            return null;
        } else {
            s = mission;
            return s;
        }
    }


}
