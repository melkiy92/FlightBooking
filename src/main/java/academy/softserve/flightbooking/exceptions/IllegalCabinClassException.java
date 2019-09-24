package academy.softserve.flightbooking.exceptions;

public class IllegalCabinClassException extends Exception {
    public IllegalCabinClassException() {
        super("IllegalCabinClassException");
    }

    public IllegalCabinClassException(String message) {
        super(message);
    }
}
