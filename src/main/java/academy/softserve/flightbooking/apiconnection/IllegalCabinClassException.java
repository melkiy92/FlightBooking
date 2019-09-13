package academy.softserve.flightbooking.apiconnection;

import org.springframework.stereotype.Component;

@Component
public class IllegalCabinClassException extends Exception {
    public IllegalCabinClassException() {
        super("IllegalCabinClassException");
    }

    public IllegalCabinClassException(String message) {
        super(message);
    }
}
