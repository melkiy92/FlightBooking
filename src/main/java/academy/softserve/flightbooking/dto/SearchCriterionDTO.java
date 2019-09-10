package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.TicketType;
import lombok.*;

import java.time.LocalDate;

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
    private Long DepartDate;
    private Long ReturnDate;
}
