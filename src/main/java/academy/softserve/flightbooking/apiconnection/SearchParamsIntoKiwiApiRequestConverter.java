package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.models.components.CabinClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@Component
public class SearchParamsIntoKiwiApiRequestConverter {

    private ParametersStringBuilder parametersStringBuilder;

    public String convertIntoRequestString(SearchCriterionDTO searchCriterionDTO)
            throws IllegalDateException, IllegalCabinClassException, UnsupportedEncodingException {

        KiwiSearchCriterionDto kiwiSearchCriterionDto = new KiwiSearchCriterionDto();
        kiwiSearchCriterionDto.setCurrency(searchCriterionDTO.getCurrencyCode());
        kiwiSearchCriterionDto.setMax_stopovers("" + Integer.MAX_VALUE);
        kiwiSearchCriterionDto.setSelected_cabins(convertCabinClass(searchCriterionDTO.getCabinClass()));
        kiwiSearchCriterionDto.setAdults("" + searchCriterionDTO.getAdults());
        kiwiSearchCriterionDto.setChildren("" + searchCriterionDTO.getChildren());
        kiwiSearchCriterionDto.setFly_from(searchCriterionDTO.getFromLocation());
        kiwiSearchCriterionDto.setFly_to(searchCriterionDTO.getToLocation());
        kiwiSearchCriterionDto.setDate_from(convertDate(searchCriterionDTO.getDepartDate()));
        kiwiSearchCriterionDto.setDate_to(convertDate(searchCriterionDTO.getDepartDate()));
        kiwiSearchCriterionDto.setReturn_from(convertDate(searchCriterionDTO.getReturnDate()));
        kiwiSearchCriterionDto.setReturn_to(convertDate(searchCriterionDTO.getReturnDate()));

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.convertValue(kiwiSearchCriterionDto, Map.class);

        return parametersStringBuilder.getParamsString(result);
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

    private String convertDate(Long dateInMs) throws IllegalDateException {
        if (dateInMs < System.currentTimeMillis()) {
            throw new IllegalDateException("Date is in the past");
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(dateInMs);
            return dateFormatter.format(date);
        }
    }

}
