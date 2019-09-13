package academy.softserve.flightbooking.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TicketDTO {
    private Long id;
    private Double price;
    private String bookingToken; //buy ticket
    private String provider;     //kiwi or skyscanner
    private List<RouteDTO> routes;
}
