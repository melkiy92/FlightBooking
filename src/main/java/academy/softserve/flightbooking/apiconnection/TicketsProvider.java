package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.apiconnection.connectors.KiwiApiConnector;
import academy.softserve.flightbooking.apiconnection.connectors.RapidApiConnector;
import academy.softserve.flightbooking.apiconnection.exceptions.ApiErrorException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TicketsProvider {
    private KiwiApiConnector kiwiApiConnector;
    private RapidApiConnector rapidApiConnector;

    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO) throws IllegalCabinClassException, ApiErrorException, UnirestException, IllegalDateException, IOException {
        List<TicketDTO> tickets = new ArrayList<>();
        tickets.addAll(kiwiApiConnector.getTickets(searchCriterionDTO));
        tickets.addAll(rapidApiConnector.getTickets(searchCriterionDTO));
// add here a loop for calculating StopDTOs
        return tickets;
    }
}
