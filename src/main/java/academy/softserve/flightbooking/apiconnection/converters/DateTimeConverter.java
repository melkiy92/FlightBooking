package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.exceptions.IllegalDateException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
    public static String convertDate(Long dateInMs, String pattern) throws IllegalDateException {
        if (dateInMs < System.currentTimeMillis()) {
            throw new IllegalDateException("Date is in the past");
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
            Date date = new Date(dateInMs);
            return dateFormatter.format(date);
        }
    }
}
