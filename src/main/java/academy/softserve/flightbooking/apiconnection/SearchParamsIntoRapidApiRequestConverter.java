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

    public String convertIntoRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {
        RapidSearchCriterionDto rapidSearchCriterionDto = new RapidSearchCriterionDto();

        rapidSearchCriterionDto.setCountry("US");
        rapidSearchCriterionDto.setCurrency(searchCriterionDTO.getCurrencyCode());
        rapidSearchCriterionDto.setLocale("en-US");
        rapidSearchCriterionDto.setOriginPlace(searchCriterionDTO.getFromLocation() + "-sky");
        rapidSearchCriterionDto.setDestinationPlace(searchCriterionDTO.getToLocation() + "-sky");
        rapidSearchCriterionDto.setOutboundDate(convertDate(searchCriterionDTO.getDepartDate(), "yyyy-MM-dd"));
        rapidSearchCriterionDto.setInboundDate(convertDate(searchCriterionDTO.getReturnDate(), "yyyy-MM-dd"));
        rapidSearchCriterionDto.setAdults("" + searchCriterionDTO.getAdults());
        rapidSearchCriterionDto.setChildren("" + searchCriterionDTO.getChildren());
        rapidSearchCriterionDto.setCabinClass(searchCriterionDTO.getCabinClass().toString());

//        System.out.println(rapidSearchCriterionDto.getCountry());
//        System.out.println(rapidSearchCriterionDto.getCurrency());
//        System.out.println(rapidSearchCriterionDto.getLocale());
//        System.out.println(rapidSearchCriterionDto.getOriginPlace());
//        System.out.println(rapidSearchCriterionDto.getDestinationPlace());
//        System.out.println(rapidSearchCriterionDto.getOutboundDate());
//        System.out.println(rapidSearchCriterionDto.getInboundDate());
//        System.out.println(rapidSearchCriterionDto.getAdults());
//        System.out.println(rapidSearchCriterionDto.getChildren());
//        System.out.println(rapidSearchCriterionDto.getCabinClass());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.convertValue(rapidSearchCriterionDto, Map.class);

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
