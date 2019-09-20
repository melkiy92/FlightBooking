package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.models.components.CabinClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class SearchParamsIntoRapidApiRequestStringConverter {

    private ParametersStringBuilder parametersStringBuilder;

    public String convertIntoRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {
        String result;
        Map<String, String> requestParamsMap = new TreeMap<>();

        requestParamsMap.put("country", "US");
        requestParamsMap.put("currency", searchCriterionDTO.getCurrencyCode());
        requestParamsMap.put("locale", "en-US");
        requestParamsMap.put("originPlace", (searchCriterionDTO.getFromLocation() + "-sky"));
        requestParamsMap.put("destinationPlace", (searchCriterionDTO.getToLocation() + "-sky"));
        requestParamsMap.put("outboundDate", convertDate(searchCriterionDTO.getDepartDate(), "yyyy-MM-dd"));
        requestParamsMap.put("inboundDate", convertDate(searchCriterionDTO.getReturnDate(), "yyyy-MM-dd"));
        requestParamsMap.put("adults", ("" + searchCriterionDTO.getAdults()));
        requestParamsMap.put("children", ("" + searchCriterionDTO.getChildren()));
        requestParamsMap.put("cabinClass", convertCabinClass(searchCriterionDTO.getCabinClass()));
        log.info("RapidSearchCriteria : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
    }

    private String convertCabinClass(CabinClass cabinClass) throws IllegalCabinClassException {
        if (cabinClass.equals(CabinClass.FIRSTCLASS)) {
            return "first";
        } else if (cabinClass.equals(CabinClass.PREMIUMECONOMY)) {
            return "premiumeconomy";
        } else if (cabinClass.equals(CabinClass.BUSINESSCLASS)) {
            return "business";
        } else if (cabinClass.equals(CabinClass.ECONOMY)) {
            return "economy";
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
