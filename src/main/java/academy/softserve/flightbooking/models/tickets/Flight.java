package academy.softserve.flightbooking.models.tickets;

import academy.softserve.flightbooking.models.components.Airline;
import academy.softserve.flightbooking.models.components.Airport;
import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


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
    @OneToOne
    @JoinColumn(name = "airline_name", referencedColumnName = "callSign")
    private Airline airline;
    private CabinClass cabinClass;
    private Long duration;
    private Long departTime;
    @OneToOne
    @JoinColumn(name = "departAirport_code", referencedColumnName = "code")
    private Airport departAirport;
    @OneToOne
    @JoinColumn(name = "departCity_code", referencedColumnName = "code")
    private City departCity;
    private Long arrivalTime;
    @OneToOne
    @JoinColumn(name = "arrivalAirport_code", referencedColumnName = "code")
    private Airport arrivalAirport;
    @OneToOne
    @JoinColumn(name = "arrivalCity_code", referencedColumnName = "code")
    private City arrivalCity;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}
