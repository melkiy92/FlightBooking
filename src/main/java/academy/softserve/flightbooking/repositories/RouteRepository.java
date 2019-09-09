package academy.softserve.flightbooking.repositories;

import academy.softserve.flightbooking.models.tickets.Route;
import academy.softserve.flightbooking.models.tickets.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

}
