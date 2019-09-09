package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.entities.CabinClass;
import academy.softserve.flightbooking.models.entities.Currency;
import academy.softserve.flightbooking.models.entities.TicketType;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriterionDTO {
    private Long id;
    private String currencyCode;
    private TicketType ticketType;
    private CabinClass cabinClass;
    private Integer adults;
    private Integer children;
    private String fromLocation;
    private String toLocation;
    private LocalDate DepartDate;
    private LocalDate ReturnDate;
}
