package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.dto.LegDTO;
import academy.softserve.flightbooking.dto.MultiCitySearchCriterionDTO;
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
public class SearchParamsIntoKiwiApiRequestStringConverter {
    private ParametersStringBuilder parametersStringBuilder;


    public String convertIntoRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {
        String result;
        Map<String, String> requestParamsMap = new TreeMap<>();

        requestParamsMap.put("currency", searchCriterionDTO.getCurrencyCode());
        requestParamsMap.put("max_stopovers", "" + Integer.MAX_VALUE);
        requestParamsMap.put("selected_cabins", CabinClassConverter.convertCabinClassIntoString(searchCriterionDTO.getCabinClass(), Providers.KIWI));
        requestParamsMap.put("adults", ("" + searchCriterionDTO.getAdults()));
        requestParamsMap.put("children", ("" + searchCriterionDTO.getChildren()));
        requestParamsMap.put("fly_from", searchCriterionDTO.getFromLocation());
        requestParamsMap.put("fly_to", searchCriterionDTO.getToLocation());
        requestParamsMap.put("partner", "picky");
        requestParamsMap.put("v","3");
        requestParamsMap.put("date_from", DateTimeConverter.convertDate(searchCriterionDTO.getDepartDate(), ApiConnectionConstants.KIWI_DATE_PATTERN));
        requestParamsMap.put("date_to", DateTimeConverter.convertDate(searchCriterionDTO.getDepartDate(), ApiConnectionConstants.KIWI_DATE_PATTERN));
        requestParamsMap.put("limit", "5");
        if(searchCriterionDTO.getTicketType().equals(TicketType.ONEWAY)) {
            requestParamsMap.put("return_from", "");
            requestParamsMap.put("return_to", "");
        } else {
            requestParamsMap.put("return_from", DateTimeConverter.convertDate(searchCriterionDTO.getReturnDate(), ApiConnectionConstants.KIWI_DATE_PATTERN));
            requestParamsMap.put("return_to", DateTimeConverter.convertDate(searchCriterionDTO.getReturnDate(), ApiConnectionConstants.KIWI_DATE_PATTERN));
        }
        log.info("KiwiSearchCriteria : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
    }

    public String convertIntoRequestString(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException{
        String result;
        Map<String, String> requestParamsMap = new TreeMap<>();

        requestParamsMap.put("currency", multiCitySearchCriterionDTO.getCurrencyCode());
        requestParamsMap.put("locale", "en");
        requestParamsMap.put("partner", "picky");

        log.info("KiwiSearchCriteria : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
    }

    public String convertIntoRequestJson(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO) throws UnsupportedEncodingException {
        String result = "{\"requests\": [";

        for (LegDTO leg : multiCitySearchCriterionDTO.getLegs()) {
            String itinerary = "{";
            Map<String, String> requestParamsMap = new TreeMap<>();

            requestParamsMap.put("fly_from", leg.getFromLocation());
            requestParamsMap.put("fly_to", leg.getToLocation());
            requestParamsMap.put("date_from", "" + leg.getDepartDate());
            requestParamsMap.put("date_to", "" + leg.getDepartDate());
            requestParamsMap.put("direct_flights", "" + 0);
            requestParamsMap.put("passengers", "" + (multiCitySearchCriterionDTO.getAdults() + multiCitySearchCriterionDTO.getChildren()));
            requestParamsMap.put("adults", "" + multiCitySearchCriterionDTO.getAdults());
            requestParamsMap.put("children", "" + multiCitySearchCriterionDTO.getChildren());
            requestParamsMap.put("infants", "" + 0);
            itinerary = itinerary + parametersStringBuilder.getParamsJsonBody(requestParamsMap);

            itinerary = itinerary + "},";
            result = result + itinerary;
        }

        result.substring(0, result.length() - 1);
        result = result + "]}";

        return result;
    }
}
