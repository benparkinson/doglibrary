package parkinsonbenjamin.doglibrary.dataobjects;

import org.json.simple.JSONObject;

public class Dog implements JSONObj {

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

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("dogId", dogId);
        ret.put("name", name);
        ret.put("breed", breed);
        ret.put("available", available);
        return ret;
    }
}
