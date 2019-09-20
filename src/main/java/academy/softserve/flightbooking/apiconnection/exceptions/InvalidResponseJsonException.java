package academy.softserve.flightbooking.apiconnection.exceptions;

public class InvalidResponseJsonException extends Exception {
    public InvalidResponseJsonException() {
        super("InvalidResponseJsonException");
    }

    public InvalidResponseJsonException(String message) {
        super(message);
    }
}
