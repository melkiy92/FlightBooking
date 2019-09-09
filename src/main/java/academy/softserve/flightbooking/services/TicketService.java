package academy.softserve.flightbooking.services;

import academy.softserve.flightbooking.models.SearchCriterion;
import academy.softserve.flightbooking.models.tickets.Flight;
import academy.softserve.flightbooking.models.tickets.Ticket;

import java.util.List;

public interface TicketService {
    Ticket getTicket(SearchCriterion searchCriterion);
}
