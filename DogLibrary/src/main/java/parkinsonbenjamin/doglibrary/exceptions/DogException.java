package parkinsonbenjamin.doglibrary.exceptions;

public class DogException extends Exception {

    public DogException(String message) {
        super(message);
    }

    public DogException(String message, Throwable cause) {
        super(message, cause);
    }

    public DogException(Exception e) {
        super(e);
    }
}
