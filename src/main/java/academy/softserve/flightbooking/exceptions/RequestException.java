package academy.softserve.flightbooking.exceptions;

public class RequestException extends Exception {
    public RequestException() {
    }

    public RequestException(String message) {
        super(message);
    }
}
