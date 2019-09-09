package academy.softserve.flightbooking.models;

import academy.softserve.flightbooking.models.entities.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    private Currency currency;
    private TicketType ticketType;
    private CabinClass cabinClass;
    private Integer adults;
    private Integer children;
    private Airport fromAirport;
    private City fromCity;
    private Country fromCountry;
    private Airport toAirport;
    private City toCity;
    private Country toCountry;
    private LocalDate DepartDate;
    private LocalDate ReturnDate; //if the ticketType is RETURNED
}
