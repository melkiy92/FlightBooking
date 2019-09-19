package academy.softserve.flightbooking.apiconnection;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapidSearchCriterionDto {
    private String country;
    private String currency;
    private String locale;
    private String originPlace;
    private String destinationPlace;
    private String outboundDate;
    private String adults;
    private String inboundDate;
    private String cabinClass;
    private String children;
}
