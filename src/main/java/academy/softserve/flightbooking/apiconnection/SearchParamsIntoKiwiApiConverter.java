package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.KiwiSearchCriterionDTO;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static academy.softserve.flightbooking.apiconnection.ParametersStringBuilder.getParamsString;

@Component
public class SearchParamsIntoKiwiApiConverter {

    public static String convertIntoRequest (SearchCriterionDTO searchCriterionDTO) throws UnsupportedEncodingException {

        KiwiSearchCriterionDTO kiwiSearchCriterionDTO = new KiwiSearchCriterionDTO(searchCriterionDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.convertValue(kiwiSearchCriterionDTO, Map.class);

        return getParamsString(result);
    }
}
