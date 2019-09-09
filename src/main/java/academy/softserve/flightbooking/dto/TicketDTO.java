package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.tickets.Route;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private Double price;
    private String bookingToken; //buy ticket
    private String provider;     //kiwi or skyscanner
    private List<RouteDTO> routes;
}
