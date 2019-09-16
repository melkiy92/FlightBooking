package academy.softserve.flightbooking.apiconnection;

public class IllegalCabinClassException extends Exception {
    public IllegalCabinClassException() {
        super("IllegalCabinClassException");
    }

    public IllegalCabinClassException(String message) {
        super(message);
    }
}
