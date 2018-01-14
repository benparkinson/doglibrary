package parkinsonbenjamin.doglibrary.dal;

import parkinsonbenjamin.doglibrary.dataobjects.Dog;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.dataobjects.Withdrawal;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Dispatches calls to a wrapped implementation of the dal to a single thread to avoid concurrent processing
 */
public class DalDispatcher implements DoggoDal {

    private final ExecutorService processor = Executors.newSingleThreadExecutor();
    private DoggoDal wrapped;

    public DalDispatcher(DoggoDal wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int addDog(String name, String breed) throws DogException {
        try {
            return processor.submit(() -> wrapped.addDog(name, breed)).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public int addUser(String username, String firstname, String surname, String favouriteBreed, String passwordHash) throws DogException {
        try {
            return processor.submit(() -> wrapped.addUser(username, firstname, surname, favouriteBreed, passwordHash)).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public void favouriteDog(int userId, int dogId) throws DogException {
        try {
            // submit callable to block called
            processor.submit((Callable<Void>) () -> {
                wrapped.favouriteDog(userId, dogId);
                return null;
            }).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public void unfavouriteDog(int userId, int dogId) throws DogException {
        try {
            // submit callable to block called
            processor.submit((Callable<Void>) () -> {
                wrapped.unfavouriteDog(userId, dogId);
                return null;
            }).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public void withdrawDog(int userId, int dogId) throws DogException {
        try {
            // submit callable to block called
            processor.submit((Callable<Void>) () -> {
                wrapped.withdrawDog(userId, dogId);
                return null;
            }).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public void returnDog(int userId) throws DogException {
        try {
            // submit callable to block called
            processor.submit((Callable<Void>) () -> {
                wrapped.returnDog(userId);
                return null;
            }).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public List<Dog> getAllDogs() throws DogException {
        try {
            return processor.submit(() -> wrapped.getAllDogs()).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DogException {
        try {
            return processor.submit(() -> wrapped.getAllUsers()).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public List<Withdrawal> getCurrentWithdrawals() throws DogException {
        try {
            return processor.submit(() -> wrapped.getCurrentWithdrawals()).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }

    @Override
    public List<Integer> getFavouritesForUser(int userId) throws DogException {
        try {
            return processor.submit(() -> wrapped.getFavouritesForUser(userId)).get();
        } catch (Exception e) {
            throw new DogException(e);
        }
    }
}
