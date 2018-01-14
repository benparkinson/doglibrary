package parkinsonbenjamin.doglibrary.dataobjects;

public class Dog {

    private int dogId;
    private String name;
    private String breed;
    private boolean available;

    public Dog(int dogId, String name, String breed) {
        this.dogId = dogId;
        this.name = name;
        this.breed = breed;
    }

    public int getDogId() {
        return dogId;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public boolean isAvailable() {
        return available;
    }

    public void withdraw() {
        this.available = false;
    }

    public void giveBack(){
        this.available = true;
    }
}
