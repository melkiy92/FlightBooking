package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.dto.LegDTO;
import academy.softserve.flightbooking.dto.MultiCitySearchCriterionDTO;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.models.components.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import static academy.softserve.flightbooking.apiconnection.converters.DateTimeConverter.*;
import static academy.softserve.flightbooking.constants.ApiConnectionConstants.*;

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
        requestParamsMap.put("date_from", convertDate(searchCriterionDTO.getDepartDate(), KIWI_DATE_PATTERN));
        requestParamsMap.put("date_to", convertDate(searchCriterionDTO.getDepartDate(), KIWI_DATE_PATTERN));
        requestParamsMap.put("limit", "5");
        if(searchCriterionDTO.getTicketType().equals(TicketType.ONEWAY)) {
            requestParamsMap.put("return_from", "");
            requestParamsMap.put("return_to", "");
        } else {
            requestParamsMap.put("return_from", convertDate(searchCriterionDTO.getReturnDate(), KIWI_DATE_PATTERN));
            requestParamsMap.put("return_to", convertDate(searchCriterionDTO.getReturnDate(), KIWI_DATE_PATTERN));
        }
        log.info("KiwiSearchCriteria : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
    }

    public String convertIntoRequestString(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException{
        String result;
        Map<String, String> requestParamsMap = new TreeMap<>();

        requestParamsMap.put("curr", multiCitySearchCriterionDTO.getCurrencyCode());
        requestParamsMap.put("locale", "en");
        requestParamsMap.put("partner", "picky");

        log.info("KiwiRequestParameters : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
    }

    public String convertIntoRequestJson(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO) throws UnsupportedEncodingException, IllegalDateException {
        String result = "{\"requests\": [";

        for (LegDTO leg : multiCitySearchCriterionDTO.getLegs()) {
            String itinerary = "{";

            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("fly_from", leg.getFromLocation());
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("fly_to", leg.getToLocation() );
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("date_from", convertDate(leg.getDepartDate(), KIWI_DATE_PATTERN));
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("date_to", convertDate((leg.getDepartDate() + 86400000L), KIWI_DATE_PATTERN));
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("direct_flights", 0);
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("passengers", (multiCitySearchCriterionDTO.getAdults() + multiCitySearchCriterionDTO.getChildren()));
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("adults", multiCitySearchCriterionDTO.getAdults());
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("children", multiCitySearchCriterionDTO.getChildren());
            itinerary = itinerary + parametersStringBuilder.convertIntoJsonParameter("infants", 0);
            itinerary = itinerary.substring(0, itinerary.length() - 1);

            itinerary = itinerary + "},";
            result = result + itinerary;
        }

        result = result.substring(0, result.length() - 1);
        result = result + "]}";
        log.info("Kiwi MultiCity Request body : " + result);

        return result;
    }
}
