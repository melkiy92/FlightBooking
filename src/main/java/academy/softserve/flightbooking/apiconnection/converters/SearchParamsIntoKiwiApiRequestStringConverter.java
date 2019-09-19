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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class SearchParamsIntoKiwiApiRequestStringConverter {
    private ParametersStringBuilder parametersStringBuilder;

    public String convertIntoRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {
        String result;
        Map<String, String> requestParamsMap = new HashMap<>();

        requestParamsMap.put("currency", searchCriterionDTO.getCurrencyCode());
        requestParamsMap.put("max_stopovers", "" + Integer.MAX_VALUE);
        requestParamsMap.put("selected_cabins", convertCabinClass(searchCriterionDTO.getCabinClass()));
        requestParamsMap.put("adults", ("" + searchCriterionDTO.getAdults()));
        requestParamsMap.put("children", ("" + searchCriterionDTO.getChildren()));
        requestParamsMap.put("fly_from", searchCriterionDTO.getFromLocation());
        requestParamsMap.put("fly_to", searchCriterionDTO.getToLocation());
        requestParamsMap.put("date_from", convertDate(searchCriterionDTO.getDepartDate(), "dd/MM/yyyy"));
        requestParamsMap.put("date_to", convertDate(searchCriterionDTO.getDepartDate(), "dd/MM/yyyy"));
        requestParamsMap.put("return_from", convertDate(searchCriterionDTO.getReturnDate(), "dd/MM/yyyy"));
        requestParamsMap.put("return_to", convertDate(searchCriterionDTO.getReturnDate(), "dd/MM/yyyy"));
        requestParamsMap.put("partner", "picky");
        requestParamsMap.put("v","3");
        log.info("KiwiSearchCriteria : " + requestParamsMap.toString());
        result = parametersStringBuilder.getParamsString(requestParamsMap);

        return result;
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
