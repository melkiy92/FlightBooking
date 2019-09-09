package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.entities.City;
import academy.softserve.flightbooking.models.tickets.Flight;
import academy.softserve.flightbooking.models.tickets.Ticket;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    private Long id;
    private String cityNameFrom;
    private String cityNameTo;
    private Duration duration;
    private List<FlightDTO> flights;
}
