package academy.softserve.flightbooking.apiconnection.connectors;

import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoKiwiApiRequestStringConverter;
import academy.softserve.flightbooking.apiconnection.desrializers.KiwiApiResponseDeserializer;
import academy.softserve.flightbooking.apiconnection.exceptions.ApiErrorException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class KiwiApiConnector {
    private SearchParamsIntoKiwiApiRequestStringConverter converter;
    private KiwiApiResponseDeserializer deserializer;

    private static final String FLIGHTS_ENDPOINT = "https://api.skypicker.com/flights?";
    private static final String MULTICITY_ENDPOINT = "https://api.skypicker.com/flights_multi?";


    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException {
        List<TicketDTO> result;

        String parameters = converter.convertIntoRequestString(searchCriterionDTO);
        log.info("Sending request to Kiwi API endpoint");
        HttpResponse<String> response = Unirest.get(FLIGHTS_ENDPOINT + parameters).asString();
        log.info("Kiwi API response status : " + response.getStatus());
        if (response.getStatus() < 300) {
            log.info("Received data from Kiwi API endpoint");
            result = deserializer.deserializeFlightsData(response.getBody(), searchCriterionDTO.getTicketType());
        } else {
            log.error("Kiwi API connection error : " + response.getBody());
            throw new ApiErrorException(response.getBody());
        }
        log.info("Tickets list ready");

        return result;
    }
}
