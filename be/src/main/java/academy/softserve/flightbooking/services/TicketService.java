package academy.softserve.flightbooking.services;

import academy.softserve.flightbooking.dto.MultiCitySearchCriterionDTO;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.exceptions.RequestException;
import academy.softserve.flightbooking.exceptions.ResponseException;

import java.util.List;

public interface TicketService {
    List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO) throws RequestException, ResponseException, NoTicketsException;
    List<TicketDTO> getMultiCityTickets(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO) throws NoTicketsException, RequestException, ResponseException;
}
