package parkinsonbenjamin.doglibrary.processor;

import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.List;
import java.util.Map;

public class LoginProcessor implements Processor {

    private static LoginProcessor instance;

    private Map<String, User> usersByUsername;
    private DoggoDal dal;

    private LoginProcessor() {
    }

    public static LoginProcessor getInstance() {
        if (instance != null) {
            instance = new LoginProcessor();
        }
        return instance;
    }

    @Override
    public void initialise(DoggoDal dal) throws DogException {
        this.dal = dal;
        initUsers();
    }

    private void initUsers() throws DogException {
        List<User> allUsers = dal.getAllUsers();
        for (User u : allUsers) {
            usersByUsername.put(u.getUsername(), u);
        }
    }

    public String login(String username, String password) throws DogException {
        User user = getUser(username);

        // hash password and compare

        return null;
    }

    private User getUser(String username) throws DogException {
        return usersByUsername.get(username);
    }

}
