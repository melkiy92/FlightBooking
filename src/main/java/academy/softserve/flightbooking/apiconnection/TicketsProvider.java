package academy.softserve.flightbooking.apiconnection;

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
        List<TicketDTO> result = new ArrayList<>();
        result.addAll(kiwiApiConnector.getTickets(searchCriterionDTO));
        result.addAll(rapidApiConnector.getTickets(searchCriterionDTO));
        return result;
    }
}
