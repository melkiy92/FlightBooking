package academy.softserve.flightbooking.apiconnection;

public class ApiErrorException extends Exception {
    public ApiErrorException() {
        super("Something went wrong with the aggregator");
    }

    public ApiErrorException(String message) {
        super(message);
    }
}
