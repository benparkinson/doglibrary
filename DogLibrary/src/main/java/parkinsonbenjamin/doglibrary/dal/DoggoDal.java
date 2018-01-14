package parkinsonbenjamin.doglibrary.dal;

import parkinsonbenjamin.doglibrary.dataobjects.Dog;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.dataobjects.Withdrawal;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.List;

public interface DoggoDal {

    int addDog(String name, String breed);
    int addUser(String username, String firstname, String surname, String favouriteBreed, String passwordHash) throws DogException;
    boolean authenticateUser(String username, String passwordHash);
    void favouriteDog(int userId, int dogId) throws DogException;
    void unfavouriteDog(int userId, int dogId) throws DogException;
    void withdrawDog(int userId, int dogId) throws DogException;
    void returnDog(int userId) throws DogException;
    List<Dog> getAllDogs() throws DogException;
    List<User> getAllUsers() throws DogException;
    List<Withdrawal> getCurrentWithdrawals() throws DogException;
    List<Integer> getFavouritesForUser(int userId) throws DogException;

}
