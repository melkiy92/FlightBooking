package academy.softserve.flightbooking.controller;

import academy.softserve.flightbooking.apiconnection.ApiErrorException;
import academy.softserve.flightbooking.apiconnection.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.IllegalDateException;
import academy.softserve.flightbooking.apiconnection.KiwiApiConnector;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static academy.softserve.flightbooking.models.components.CabinClass.ECONOMY;
import static academy.softserve.flightbooking.models.components.TicketType.ONEWAY;
import static java.time.LocalDate.of;

@RestController
public class FlightsController {

    private KiwiApiConnector kiwiApiConnector;

    private SearchCriterionDTO searchCriterion;

    public FlightsController(KiwiApiConnector kiwiApiConnector) {
        this.kiwiApiConnector = kiwiApiConnector;
        searchCriterion = new SearchCriterionDTO();
        searchCriterion.setId(1L);
        searchCriterion.setCurrencyCode("USD");
        searchCriterion.setTicketType(ONEWAY);
        searchCriterion.setCabinClass(ECONOMY);
        searchCriterion.setAdults(1);
        searchCriterion.setChildren(0);
        searchCriterion.setFromLocation("OZH");
        searchCriterion.setToLocation("KBP");
        searchCriterion.setDepartDate(1574028000000L);
        searchCriterion.setReturnDate(1576620000000L);
    }

    @GetMapping("/test/kiwi")
    public @ResponseBody List<TicketDTO> getKiwiData(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException {
        searchCriterionDTO = searchCriterion;
        return kiwiApiConnector.getFlightsData(searchCriterionDTO);
    }
}
