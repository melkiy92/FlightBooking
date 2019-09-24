package academy.softserve.flightbooking.exceptions;

public class InvalidResponseJsonException extends Exception {
    public InvalidResponseJsonException() {
        super("InvalidResponseJsonException");
    }

    public InvalidResponseJsonException(String message) {
        super(message);
    }
}
