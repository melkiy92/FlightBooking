package academy.softserve.flightbooking.services.servicesImpl;

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
        long stopDuration = 0L;
        String airportCode = "";

        if (flightDTOList.size() <= 1) {
            return stopDTOList;
        }

        /*
         * in other cases, if the "size" of the "flightDTOList" is equal "2" or more
         */
        for (int i = 0; i < flightDTOList.size() - 1; i++) {
            if (flightDTOList.get(i).getArrivalCityName().equals(flightDTOList.get(i + 1).getDepartCityName())) {
                stopDuration = flightDTOList.get(i + 1).getDepartTime() - flightDTOList.get(i).getArrivalTime();
                airportCode = flightDTOList.get(i).getArrivalCityName() + " " + flightDTOList.get(i).getArrivalAirportCode();
            }
            createStopDTO(stopDTOList, stopDuration, airportCode);
        }

        return stopDTOList;
    }

    private void createStopDTO(List<StopDTO> stopDTOList, Long duration, String airportCode) {
        StopDTO stopDTOForEmptyFlight = new StopDTO(duration, airportCode);
        stopDTOList.add(stopDTOForEmptyFlight);
    }
}
