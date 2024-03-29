package academy.softserve.flightbooking.repositories;

import academy.softserve.flightbooking.models.tickets.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
