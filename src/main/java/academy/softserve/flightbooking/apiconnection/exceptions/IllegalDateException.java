package academy.softserve.flightbooking.apiconnection.exceptions;

public class IllegalDateException extends Exception {
    public IllegalDateException() {
        super("IllegalDateException");
    }

    public IllegalDateException(String message) {
        super(message);
    }
}
