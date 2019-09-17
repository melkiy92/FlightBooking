package academy.softserve.flightbooking.services;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.StopDTO;

import java.util.List;

public interface FlightStopsCalculationService {

    /**
     * calculate the duration of stops between the flights for the creating of the "RouteDTO" object
     *
     * @param flightDTOList with some flights for calculation of the flight stop's duration from users search
     *                      where the "List<StopDTO> stops" is "null"
     *                      in the "academy.softserve.flightbooking.dto.RouteDTO.java"
     * @return the object of "StopDTO" with calculated List<StopDTO> stopsDuration"
     */
    List<StopDTO> calculateStopsBetweenFlights(List<FlightDTO> flightDTOList);

}
