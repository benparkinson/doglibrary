package parkinsonbenjamin.doglibrary.dataobjects;

public class User {

    private int userId;
    private String firstName;
    private String surname;
    private String username;
    private String favouriteBreed;
    private boolean withdrawnDog;
    private Integer withdrawnDogId;
    private String passwordHash;

    public User(int userId, String firstName, String surname, String username, String favouriteBreed, String passwordHash) {
        this.userId = userId;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.favouriteBreed = favouriteBreed;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getFavouriteBreed() {
        return favouriteBreed;
    }

    public boolean hassWithdrawnDog() {
        return withdrawnDog;
    }

    public void withdrawDog(int dogId) {
        withdrawnDogId = dogId;
        withdrawnDog = true;
    }

    private void returnDog() {
        withdrawnDogId = null;
        withdrawnDog = false;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
