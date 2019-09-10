package academy.softserve.flightbooking.models.tickets;

import academy.softserve.flightbooking.models.components.Airline;
import academy.softserve.flightbooking.models.components.Airport;
import academy.softserve.flightbooking.models.components.City;
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
    private Long departDate;
    @OneToOne
    @JoinColumn(name = "airline_name")
    private Airline airline;
    private Long duration;
    private Long departTime;
    @OneToOne
    @JoinColumn(name = "departAirport_code")
    private Airport departAirport;
    @OneToOne
    @JoinColumn(name = "departCity_name")
    private City departCity;
    private Long arrivalTime;
    @OneToOne
    @JoinColumn(name = "arrivalAirport_code")
    private Airport arrivalAirport;
    @OneToOne
    @JoinColumn(name = "arrivalCity_name")
    private City arrivalCity;
    @ManyToOne
    private Route route;
}
