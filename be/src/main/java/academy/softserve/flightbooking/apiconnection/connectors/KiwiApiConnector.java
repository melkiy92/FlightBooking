package academy.softserve.flightbooking.apiconnection.connectors;

import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoKiwiApiRequestStringConverter;
import academy.softserve.flightbooking.apiconnection.desrializers.KiwiApiResponseDeserializer;
import academy.softserve.flightbooking.exceptions.ApiErrorException;
import academy.softserve.flightbooking.exceptions.DeserializationException;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.constants.ApiConnectionConstants;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class KiwiApiConnector {
    private SearchParamsIntoKiwiApiRequestStringConverter converter;
    private KiwiApiResponseDeserializer deserializer;


    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws UnsupportedEncodingException, IllegalDateException, IllegalCabinClassException,
            DeserializationException, ApiErrorException, UnirestException, NoTicketsException {
        List<TicketDTO> result;

        String parameters = converter.convertIntoRequestString(searchCriterionDTO);
        log.info("Sending request to Kiwi API endpoint : " + ApiConnectionConstants.KIWI_FLIGHTS_ENDPOINT + parameters);
        HttpResponse<String> response = Unirest.get(ApiConnectionConstants.KIWI_FLIGHTS_ENDPOINT + parameters).asString();
        log.info("Kiwi API response status : " + response.getStatus());
        if (response.getStatus() < 300) {
            log.info("Received data from Kiwi API endpoint");
            result = deserializer.deserializeFlightsData(response.getBody(), searchCriterionDTO.getTicketType());
        } else {
            log.error("Kiwi API connection error : " + response.getBody());
            throw new ApiErrorException("Kiwi API connection error : " + response.getBody());
        }
        log.info("Tickets list ready");

        return result;
    }
}
