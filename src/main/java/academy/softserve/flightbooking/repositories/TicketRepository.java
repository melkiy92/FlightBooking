package academy.softserve.flightbooking.repositories;

import academy.softserve.flightbooking.models.tickets.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {

//    List<Ticket> findAllByPrice(double price, Pageable pageable);
}
