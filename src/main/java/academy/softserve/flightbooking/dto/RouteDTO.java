package academy.softserve.flightbooking.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RouteDTO {
    private Long id;
    private String cityNameFrom;
    private String cityNameTo;
    private Long duration;
    private List<FlightDTO> flights;
}
