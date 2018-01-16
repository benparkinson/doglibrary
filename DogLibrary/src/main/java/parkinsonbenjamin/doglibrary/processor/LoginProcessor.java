package parkinsonbenjamin.doglibrary.processor;

import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.List;

public class LoginProcessor implements Processor {

    private final DoggoDal dal;
    private List<User> allUsers;

    public LoginProcessor(DoggoDal dal) {
        this.dal = dal;
    }

    @Override
    public void initialise() throws DogException {
        allUsers = dal.getAllUsers();
    }
}
