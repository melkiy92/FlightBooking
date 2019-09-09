package academy.softserve.flightbooking.models;

import academy.softserve.flightbooking.models.components.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "search_criteria")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
    private LocalDate DepartDate;
    private LocalDate ReturnDate; //if the ticketType is RETURNED
}
