package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.components.CabinClass;
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
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private Long departDate;
    private String airlineName;
    private CabinClass cabinClass;
    private Long duration;
    private Long departTime;
    private String departAirportCode;
    private String departCityName;
    private Long arrivalTime;
    private String arrivalAirportCode;
    private String arrivalCityName;
}
