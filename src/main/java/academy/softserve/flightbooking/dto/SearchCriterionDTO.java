package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.TicketType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SearchCriterionDTO {
    private Long id;
    private String currencyCode;
    private TicketType ticketType;
    private CabinClass cabinClass;
    private Integer adults;
    private Integer children;
    private String fromLocation;
    private String toLocation;
    private Long departDate;
    private Long returnDate;
}
