package academy.softserve.flightbooking.models.tickets;

import academy.softserve.flightbooking.models.components.Airline;
import academy.softserve.flightbooking.models.components.Airport;
import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.City;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private Long departDate;
    @OneToOne
    @JoinColumn(name = "airline_name")
    private Airline airline;
    private CabinClass cabinClass;
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
