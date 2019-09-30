package academy.softserve.flightbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegDTO {
    private String fromLocation;
    private String toLocation;
    private Long departDate;
}
