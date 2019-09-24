package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.constants.ApiConnectionConstants;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.models.components.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
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
        requestParamsMap.put("outboundDate", DateTimeConverter.convertDate(searchCriterionDTO.getDepartDate(), ApiConnectionConstants.RAPID_DATE_PATTERN));
        requestParamsMap.put("adults", ("" + searchCriterionDTO.getAdults()));
        requestParamsMap.put("children", ("" + searchCriterionDTO.getChildren()));
        requestParamsMap.put("cabinClass", CabinClassConverter.convertCabinClassIntoString(searchCriterionDTO.getCabinClass(), Providers.RAPID));
        if(searchCriterionDTO.getTicketType().equals(TicketType.ONEWAY)){
            requestParamsMap.put("inboundDate", "");
        } else {
            requestParamsMap.put("inboundDate", DateTimeConverter.convertDate(searchCriterionDTO.getReturnDate(), ApiConnectionConstants.RAPID_DATE_PATTERN));
        }
        log.info("RapidSearchCriteria : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
    }
}
