package parkinsonbenjamin.doglibrary.processor;

import org.json.simple.JSONArray;
import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dataobjects.Dog;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.dataobjects.Withdrawal;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawalProcessor implements Processor  {

    private final DoggoDal dal;
    private final Map<Integer, User> usersById = new HashMap<>();
    private final Map<Integer, Dog> dogsById = new HashMap<>();

    public WithdrawalProcessor(DoggoDal dal) {
        this.dal = dal;
    }

    @Override
    public void initialise() throws DogException {
        initUserMap();
        initDogMap();
        List<Withdrawal> currentWithdrawals = dal.getCurrentWithdrawals();
        for (Withdrawal withdrawal : currentWithdrawals) {
            User user = usersById.get(withdrawal.getUserId());
            user.withdrawDog(withdrawal.getDogId());
            Dog dog = dogsById.get(withdrawal.getDogId());
            dog.withdraw();
        }
    }

    private void initDogMap() throws DogException {
        usersById.clear();
        List<Dog> allDogs = dal.getAllDogs();
        for (Dog d : allDogs) {
            dogsById.put(d.getDogId(), d);
        }
    }

    private void initUserMap() throws DogException {
        usersById.clear();
        List<User> allUsers = dal.getAllUsers();
        for (User u : allUsers) {
            usersById.put(u.getUserId(), u);
        }
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
