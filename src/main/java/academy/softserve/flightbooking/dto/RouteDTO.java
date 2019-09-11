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
    private Duration duration;
    private List<FlightDTO> flights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityNameFrom() {
        return cityNameFrom;
    }

    public void setCityNameFrom(String cityNameFrom) {
        this.cityNameFrom = cityNameFrom;
    }

    public String getCityNameTo() {
        return cityNameTo;
    }

    public void setCityNameTo(String cityNameTo) {
        this.cityNameTo = cityNameTo;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightDTO> flights) {
        this.flights = flights;
    }
}
