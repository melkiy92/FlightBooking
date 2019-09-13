package academy.softserve.flightbooking.dto;

import lombok.*;

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
    private Long duration;
    private List<FlightDTO> flights;
}
