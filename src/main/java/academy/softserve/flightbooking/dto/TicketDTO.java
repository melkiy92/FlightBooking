package academy.softserve.flightbooking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


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
