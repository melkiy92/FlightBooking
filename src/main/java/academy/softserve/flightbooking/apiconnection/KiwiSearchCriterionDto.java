package academy.softserve.flightbooking.apiconnection;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KiwiSearchCriterionDto {
    private String currency;
//    private String max_stopovers;
    private String selected_cabins;
    private String adults;
    private String children;
    private String fly_from;
    private String fly_to;
    private String date_from;
    private String date_to;
    private String return_from;
    private String return_to;
    /* Note, that these two final parameters must be typed the same way as in the request params and they must not
    * be static, because ParametersStringBuilder will ignore them when constructing the request string that will
    * lead to error response from Kiwi API*/
    private final String partner = "picky";
    private final String v = "3";
}


