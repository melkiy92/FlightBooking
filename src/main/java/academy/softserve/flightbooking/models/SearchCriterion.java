package academy.softserve.flightbooking.models;

import academy.softserve.flightbooking.models.components.Currency;
import academy.softserve.flightbooking.models.components.City;
import academy.softserve.flightbooking.models.components.Airport;
import academy.softserve.flightbooking.models.components.Country;
import academy.softserve.flightbooking.models.components.TicketType;
import academy.softserve.flightbooking.models.components.CabinClass;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "search_criteria")
public class SearchCriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "currency_code")
    private Currency currency;
    private TicketType ticketType;
    private CabinClass cabinClass;
    private Integer adults;
    private Integer children;
    @OneToOne
    @JoinColumn(name = "fromAirport_code")
    private Airport fromAirport;
    @OneToOne
    @JoinColumn(name = "fromCity_name")
    private City fromCity;
    @OneToOne
    @JoinColumn(name = "fromCountry_name")
    private Country fromCountry;
    @OneToOne
    @JoinColumn(name = "toAirport_code")
    private Airport toAirport;
    @OneToOne
    @JoinColumn(name = "toCity_name")
    private City toCity;
    @OneToOne
    @JoinColumn(name = "toCountry_name")
    private Country toCountry;
    private Long DepartDate;
    private Long ReturnDate; //if the ticketType is RETURNED
}
