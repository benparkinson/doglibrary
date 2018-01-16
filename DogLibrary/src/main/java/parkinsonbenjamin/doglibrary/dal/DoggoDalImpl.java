package parkinsonbenjamin.doglibrary.dal;

import org.joda.time.DateTime;
import parkinsonbenjamin.doglibrary.dataobjects.Dog;
import parkinsonbenjamin.doglibrary.dataobjects.User;
import parkinsonbenjamin.doglibrary.dataobjects.Withdrawal;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DoggoDalImpl implements DoggoDal {

    private final DbConnectionFactory connectionFactory;
    private AtomicReference<List<User>> cachedUsers = new AtomicReference<>();
    private AtomicReference<List<Dog>> cachedDogs = new AtomicReference<>();

    public DoggoDalImpl(String host, int port, String dbName, String username, String password) {
        connectionFactory = new DbConnectionFactory(host, port, dbName, username, password);
    }

    @Override
    public int addDog(String name, String breed) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call addDog(?,?,?)}");
            callableStatement.setString(1, name);
            callableStatement.setString(2, breed);
            callableStatement.registerOutParameter(3, Types.INTEGER);

            callableStatement.execute();
            int newDogId = callableStatement.getInt(3);
            invalidateDogCache();
            return newDogId;
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught adding dog! Name: %s, Breed: %s", name, breed), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    private void invalidateDogCache() {
        cachedDogs.set(null);
    }

    @Override
    public int addUser(String username, String firstname, String surname, String favouriteBreed, String
            passwordHash) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call addUser(?,?,?,?,?,?)}");
            callableStatement.setString(1, username);
            callableStatement.setString(2, firstname);
            callableStatement.setString(3, surname);
            callableStatement.setString(4, favouriteBreed);
            callableStatement.setString(5, passwordHash);
            callableStatement.registerOutParameter(6, Types.INTEGER);

            callableStatement.execute();
            int newUserId = callableStatement.getInt(6);

            invalidateUserCache();
            return newUserId;
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught adding user! Username: %s, Firstname: %s, " +
                            "Surname: %s, favouriteBreed: %s, Passwordhash: %s", username, firstname, surname,
                    favouriteBreed, passwordHash), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    private void invalidateUserCache() {
        cachedUsers.set(null);
    }

    @Override
    public void favouriteDog(int userId, int dogId) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call favouriteDog(?,?)}");
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, dogId);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught favouriting dog! UserId: %s, DogId: %s", userId,
                    dogId), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public void unfavouriteDog(int userId, int dogId) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call unFavouriteDog(?,?)}");
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, dogId);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught unfavouriting dog! UserId: %s, DogId: %s", userId,
                    dogId), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public void withdrawDog(int userId, int dogId) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call withdrawDog(?,?)}");
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, dogId);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught withdrawing dog! UserId: %s, DogId: %s", userId, dogId), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public void returnDog(int userId) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call returnDog(?)}");
            callableStatement.setInt(1, userId);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught returning dog! UserId: %s", userId), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public List<Dog> getAllDogs() throws DogException {
        List<Dog> dogCache = this.cachedDogs.get();
        if (dogCache != null) {
            return dogCache;
        }

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            List<Dog> dogs = new ArrayList<>();
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call getAllDogs()}");
            if (callableStatement.execute()) {
                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    int dogid = resultSet.getInt("dogid");
                    String name = resultSet.getString("name");
                    String breedName = resultSet.getString("breedname");

                    dogs.add(new Dog(dogid, name, breedName));
                }
            }
            cachedDogs.set(dogs);
            return dogs;
        } catch (SQLException e) {
            throw new DogException("Exception caught getting dogs!", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public List<User> getAllUsers() throws DogException {
        List<User> userCache = this.cachedUsers.get();
        if (userCache != null) {
            return userCache;
        }

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            List<User> users = new ArrayList<>();
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call getAllUsers()}");
            if (callableStatement.execute()) {
                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    int userid = resultSet.getInt("userid");
                    String username = resultSet.getString("username");
                    String firstname = resultSet.getString("firstname");
                    String surname = resultSet.getString("surname");
                    String favouriteBreed = resultSet.getString("breedname");
                    String passwordHash = resultSet.getString("passwordhash");

                    users.add(new User(userid, firstname, surname, username, favouriteBreed, passwordHash));
                }
            }
            cachedUsers.set(users);
            return users;
        } catch (SQLException e) {
            throw new DogException("Exception caught getting users!", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public List<Withdrawal> getCurrentWithdrawals() throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            List<Withdrawal> withdrawals = new ArrayList<>();
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call getCurrentWithdrawals()}");
            if (callableStatement.execute()) {
                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    int userid = resultSet.getInt("userid");
                    int dogid = resultSet.getInt("dogid");
                    DateTime withdrawnfrom = new DateTime(resultSet.getDate("withdrawnfrom"));
                    DateTime withdrawnto = new DateTime(resultSet.getDate("withdrawnto"));

                    withdrawals.add(new Withdrawal(dogid, userid, withdrawnfrom, withdrawnto));
                }
            }
            return withdrawals;
        } catch (SQLException e) {
            throw new DogException("Exception caught getting withdrawals!", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }

    @Override
    public List<Integer> getFavouritesForUser(int userId) throws DogException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            List<Integer> faves = new ArrayList<>();
            connection = connectionFactory.getConnection();
            callableStatement = connection.prepareCall("{call getFavesForUser(?)}");
            callableStatement.setInt(1, userId);
            if (callableStatement.execute()) {
                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    int dogid = resultSet.getInt("dogid");
                    faves.add(dogid);
                }
            }
            return faves;
        } catch (SQLException e) {
            throw new DogException(String.format("Exception caught getting favourites for user! UserId: %s", userId), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                throw new DogException("Could not close connection/statement!", e);
            }
        }
    }
}
