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
public class RouteDTO {
    private Long id;
    private String cityNameFrom;
    private String cityNameTo;
    private Long duration;
    private List<FlightDTO> flights;
    private List<StopDTO> stops;
}
