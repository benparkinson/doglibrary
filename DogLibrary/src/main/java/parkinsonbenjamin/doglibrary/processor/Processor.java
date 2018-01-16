package parkinsonbenjamin.doglibrary.processor;

import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

public interface Processor {

    void initialise(DoggoDal dal) throws DogException;

}
