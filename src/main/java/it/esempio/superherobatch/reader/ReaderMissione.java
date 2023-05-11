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

    public static final Iterator ITERATOR = Utils.generatoreDiMissione(10).iterator();



    @Override
    public Missione read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return ITERATOR.hasNext() ? (Missione) ITERATOR.next() : null;
    }
}
