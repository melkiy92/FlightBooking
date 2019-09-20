package academy.softserve.flightbooking.services;

import academy.softserve.flightbooking.apiconnection.exceptions.ApiErrorException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.apiconnection.exceptions.InvalidResponseJsonException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.models.SearchCriterion;
import academy.softserve.flightbooking.models.tickets.Ticket;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO) throws IllegalCabinClassException, ApiErrorException, UnirestException, IllegalDateException, IOException, InvalidResponseJsonException;
}
