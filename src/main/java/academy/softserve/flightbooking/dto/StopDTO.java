package academy.softserve.flightbooking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopDTO {
    private RouteDTO routeDTO;
    private Long duration;
    private String airportCode;

}
