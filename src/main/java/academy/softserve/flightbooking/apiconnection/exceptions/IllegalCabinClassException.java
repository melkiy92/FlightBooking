package academy.softserve.flightbooking.apiconnection.exceptions;

public class IllegalCabinClassException extends Exception {
    public IllegalCabinClassException() {
        super("IllegalCabinClassException");
    }

    public IllegalCabinClassException(String message) {
        super(message);
    }
}
