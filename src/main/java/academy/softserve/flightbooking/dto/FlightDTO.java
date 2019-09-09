package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.entities.Airline;
import academy.softserve.flightbooking.models.entities.Airport;
import academy.softserve.flightbooking.models.entities.City;
import academy.softserve.flightbooking.models.tickets.Route;
import lombok.*;

import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private LocalDate departDate;
    private String airlineName;
    private Duration duration;
    private LocalTime departTime;
    private String departAirportCode;
    private String departCityName;
    private LocalTime arrivalTime;
    private String arrivalAirportCode;
    private String arrivalCityName;
}
