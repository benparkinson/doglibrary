package parkinsonbenjamin.doglibrary.processor;

import org.json.simple.JSONArray;
import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dataobjects.Dog;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.List;

public class DogProcessor {

    private final DoggoDal dal;

    public DogProcessor(DoggoDal dal) {
        this.dal = dal;
    }

    public String getAllDogs() throws DogException {
        List<Dog> dogs = dal.getAllDogs();
        JSONArray array = new JSONArray();
        for (Dog d : dogs) {
            array.add(d.toJSONObject().toJSONString());
        }
        return array.toJSONString();
    }
}
