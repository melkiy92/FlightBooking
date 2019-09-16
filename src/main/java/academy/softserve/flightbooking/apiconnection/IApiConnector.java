package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

public interface IApiConnector {

    List<TicketDTO> getFlightsData(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException;
}
