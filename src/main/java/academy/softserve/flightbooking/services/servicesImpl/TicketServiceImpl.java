package academy.softserve.flightbooking.services.servicesImpl;

import academy.softserve.flightbooking.models.SearchCriterion;
import academy.softserve.flightbooking.models.tickets.Flight;
import academy.softserve.flightbooking.models.tickets.Ticket;
import academy.softserve.flightbooking.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Override
    public List<Ticket> getTickets(SearchCriterion searchCriterion) {
        return null;
    }
}
