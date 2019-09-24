package academy.softserve.flightbooking.exceptions;

public class NoTicketsException extends Exception {
    public NoTicketsException() {
    }

    public NoTicketsException(String message) {
        super(message);
    }
}
