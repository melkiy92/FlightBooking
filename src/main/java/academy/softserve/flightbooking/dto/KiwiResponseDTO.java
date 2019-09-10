package academy.softserve.flightbooking.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiwiResponseDTO {
    private String searchId;
    private List<TicketDTO> tickets;
    private String currency;
    private Integer results;
}
