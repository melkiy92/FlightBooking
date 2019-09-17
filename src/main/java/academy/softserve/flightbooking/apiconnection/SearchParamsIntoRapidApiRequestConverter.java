package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.TicketType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Component
public class SearchParamsIntoRapidApiRequestConverter {

    private ParametersStringBuilder parametersStringBuilder;

    public String convertIntoKiwiRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {
        KiwiSearchCriterion kiwiSearchCriterion = new KiwiSearchCriterion();

        kiwiSearchCriterion.setCurrency(searchCriterionDTO.getCurrencyCode());
        kiwiSearchCriterion.setMax_stopovers(getMaxStopoversByTicketType(searchCriterionDTO.getTicketType()));
        kiwiSearchCriterion.setSelected_cabins(convertCabinClass(searchCriterionDTO.getCabinClass()));
        kiwiSearchCriterion.setAdults("" + searchCriterionDTO.getAdults());
        kiwiSearchCriterion.setChildren("" + searchCriterionDTO.getChildren());
        kiwiSearchCriterion.setFly_from(searchCriterionDTO.getFromLocation());
        kiwiSearchCriterion.setFly_to(searchCriterionDTO.getToLocation());
        kiwiSearchCriterion.setDate_from(convertDate(searchCriterionDTO.getDepartDate(), "dd/MM/yyyy"));
        kiwiSearchCriterion.setReturn_from(convertDate(searchCriterionDTO.getReturnDate(), "dd/MM/yyyy"));

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.convertValue(kiwiSearchCriterion, Map.class);

        return parametersStringBuilder.getParamsString(result);
    }

    public String convertIntoRapidRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {
        RapidSearchCriterion rapidSearchCriterion = new RapidSearchCriterion();

        rapidSearchCriterion.setCountry("US");
//        System.out.println(rapidSearchCriterion.getCountry());

        rapidSearchCriterion.setCurrency(searchCriterionDTO.getCurrencyCode());
//        System.out.println(rapidSearchCriterion.getCurrency());

        rapidSearchCriterion.setLocale("en-US");
//        System.out.println(rapidSearchCriterion.getLocale());

        rapidSearchCriterion.setOriginPlace(searchCriterionDTO.getFromLocation() + "-sky");
//        System.out.println(rapidSearchCriterion.getOriginPlace());

        rapidSearchCriterion.setDestinationPlace(searchCriterionDTO.getToLocation() + "-sky");
//        System.out.println(rapidSearchCriterion.getDestinationPlace());

        rapidSearchCriterion.setOutboundDate(convertDate(searchCriterionDTO.getDepartDate(), "yyyy-MM-dd"));
//        System.out.println(rapidSearchCriterion.getOutboundDate());

        rapidSearchCriterion.setInboundDate(convertDate(searchCriterionDTO.getReturnDate(), "yyyy-MM-dd"));
//        System.out.println(rapidSearchCriterion.getInboundDate());

        rapidSearchCriterion.setAdults("" + searchCriterionDTO.getAdults());
//        System.out.println(rapidSearchCriterion.getAdults());

        rapidSearchCriterion.setChildren("" + searchCriterionDTO.getChildren());
//        System.out.println(rapidSearchCriterion.getChildren());

        rapidSearchCriterion.setCabinClass(searchCriterionDTO.getCabinClass().toString());
//        System.out.println(rapidSearchCriterion.getCabinClass());


        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.convertValue(rapidSearchCriterion, Map.class);

        return parametersStringBuilder.getParamsString(result);
    }

    private String getMaxStopoversByTicketType(TicketType ticketType) {
        if (ticketType.equals(TicketType.ONEWAY)) {
            return "" + 0;
        } else {
            return "" + Integer.MAX_VALUE;
        }
    }

    private String convertCabinClass(CabinClass cabinClass) throws IllegalCabinClassException {
        if (cabinClass.equals(CabinClass.FIRSTCLASS)) {
            return "F";
        } else if (cabinClass.equals(CabinClass.PREMIUMECONOMY)) {
            return "W";
        } else if (cabinClass.equals(CabinClass.BUSINESSCLASS)) {
            return "C";
        } else if (cabinClass.equals(CabinClass.ECONOMY)) {
            return "M";
        } else {
            throw new IllegalCabinClassException();
        }
    }

    private String convertDate(Long dateInMs, String pattern) throws IllegalDateException {
        if (dateInMs < System.currentTimeMillis()) {
            throw new IllegalDateException("Date is in the past");
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
            Date date = new Date(dateInMs);
            return dateFormatter.format(date);
        }
    }

}
