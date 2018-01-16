package parkinsonbenjamin.doglibrary.processor;

import org.json.simple.JSONArray;
import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dataobjects.Dog;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.dataobjects.Withdrawal;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawalProcessor implements Processor {

    private DoggoDal dal;
    private final Map<Integer, User> usersById = new HashMap<>();
    private final Map<Integer, Dog> dogsById = new HashMap<>();

    public WithdrawalProcessor() {

    }

    @Override
    public void initialise(DoggoDal dal) throws DogException {
        this.dal = dal;
        initUserMap();
        initDogMap();
        initWithdrawals();
    }

    private void initUserMap() throws DogException {
        usersById.clear();
        List<User> allUsers = dal.getAllUsers();
        for (User u : allUsers) {
            usersById.put(u.getUserId(), u);
        }
    }

    private void initDogMap() throws DogException {
        usersById.clear();
        List<Dog> allDogs = dal.getAllDogs();
        for (Dog d : allDogs) {
            dogsById.put(d.getDogId(), d);
        }
    }

    private void initWithdrawals() throws DogException {
        List<Withdrawal> currentWithdrawals = dal.getCurrentWithdrawals();
        for (Withdrawal withdrawal : currentWithdrawals) {
            User user = getUser(withdrawal.getUserId());
            user.withdrawDog(withdrawal.getDogId());
            Dog dog = getDog(withdrawal.getDogId());
            dog.withdraw();
        }
    }

    private User getUser(int userId) throws DogException {
        User user = usersById.get(userId);
        if (user != null)
            return user;

        initUserMap();
        user = usersById.get(userId);
        if (user == null) {
            throw new DogException(String.format("Unable to find User with Id: %d", userId));
        }
        return user;
    }

    private Dog getDog(int dogId) throws DogException {
        Dog dog = dogsById.get(dogId);
        if (dog != null)
            return dog;

        initDogMap();
        dog = dogsById.get(dogId);
        if (dog == null) {
            throw new DogException(String.format("Unable to find Dog with Id: %d", dogId));
        }
        return dog;
    }

    public String getAllDogs() {
        Collection<Dog> dogs = dogsById.values();
        JSONArray array = new JSONArray();
        for (Dog d : dogs) {
            array.add(d.toJSONObject().toJSONString());
        }
        return array.toJSONString();
    }
}
