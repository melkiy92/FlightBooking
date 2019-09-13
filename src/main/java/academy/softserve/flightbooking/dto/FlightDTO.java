package academy.softserve.flightbooking.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private Long departDate;
    private String airlineName;
    private Long duration;
    private Long departTime;
    private String departAirportCode;
    private String departCityName;
    private Long arrivalTime;
    private String arrivalAirportCode;
    private String arrivalCityName;
}
