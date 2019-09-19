package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.apiconnection.ParametersStringBuilder;
import academy.softserve.flightbooking.apiconnection.RapidSearchCriterionDto;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.TicketType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class SearchParamsIntoRapidApiRequestStringConverter {

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
        rapidSearchCriterionDto.setCabinClass(convertCabinClass(searchCriterionDTO.getCabinClass()));
        log.info("RapidSearchCriterionDto : " + rapidSearchCriterionDto.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.convertValue(rapidSearchCriterionDto, Map.class);

        return parametersStringBuilder.getParamsString(result);
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
