package academy.softserve.flightbooking.services.impl;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.StopDTO;
import academy.softserve.flightbooking.services.FlightStopsCalculationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightStopsCalculationServiceImpl implements FlightStopsCalculationService {


    @Override
    public List<StopDTO> calculateStopsBetweenFlights(List<FlightDTO> flightDTOList) {

        List<StopDTO> stopDTOList = new ArrayList<>();

        if (flightDTOList.size() <= 1) {
            return stopDTOList;
        }

        /*
         * in other cases, if the "size" of the "flightDTOList" is equal "2" or more
         */
        for (int i = 0; i < flightDTOList.size() - 1; i++) {
            Long stopDuration = flightDTOList.get(i + 1).getDepartTime() - flightDTOList.get(i).getArrivalTime();
            String airportCode = flightDTOList.get(i).getArrivalAirportCode();
            StopDTO stopDTO = new StopDTO(stopDuration, airportCode);
            stopDTOList.add(stopDTO);
        }

        return stopDTOList;
    }

}
