package parkinsonbenjamin.doglibrary.dataobjects;

import org.joda.time.DateTime;

public class Withdrawal {

    private int dogId;
    private int userId;
    private DateTime from;
    private DateTime to;

    public Withdrawal(int dogId, int userId, DateTime from, DateTime to) {
        this.dogId = dogId;
        this.userId = userId;
        this.from = from;
        this.to = to;
    }

    public int getDogId() {
        return dogId;
    }

    public int getUserId() {
        return userId;
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTo() {
        return to;
    }
}
