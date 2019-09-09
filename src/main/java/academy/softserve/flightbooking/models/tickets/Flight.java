package academy.softserve.flightbooking.models.tickets;

import academy.softserve.flightbooking.models.entities.Airline;
import academy.softserve.flightbooking.models.entities.Airport;
import academy.softserve.flightbooking.models.entities.City;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "flights")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private LocalDate departDate;
    private Airline airline;
    private Duration duration;
    private LocalTime departTime;
    private Airport departAirport;
    private City departCity;
    private LocalTime arrivalTime;
    private Airport arrivalAirport;
    private City arrivalCity;
    @ManyToOne
    private Route route;
}
