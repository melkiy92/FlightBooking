package academy.softserve.flightbooking.apiconnection.connectors;

import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoRapidApiRequestStringConverter;
import academy.softserve.flightbooking.apiconnection.desrializers.RapidApiResponseDeserializer;
import academy.softserve.flightbooking.apiconnection.exceptions.ApiErrorException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
public class RapidApiConnector {
    private SearchParamsIntoRapidApiRequestStringConverter converter;
    private RapidApiResponseDeserializer deserializer;

    private static final String POST_ENDPOINT = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0/";
    private static final String GET_ENDPOINT = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/";
    private static final String X_RAPIDAPI_HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com" ;
    private static final String X_RAPIDAPI_KEY = "5ccd3b53abmshb992db8efa8fccep18a986jsn88569fa32f67";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";


    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException {
        List<TicketDTO> result;

        String parameters = converter.convertIntoRequestString(searchCriterionDTO);
        log.info("Creating session at Rapid API endpoint");
        HttpResponse<JsonNode> sessionCreationResponse = Unirest.post(POST_ENDPOINT)
                .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                .header("Content-Type", CONTENT_TYPE)
                .body(parameters)
                .asJson();
        log.error("Rapid API session creation response status : " + sessionCreationResponse.getStatus());
        if (sessionCreationResponse.getStatus() >= 300) {
            log.error("Session creation response error : " + sessionCreationResponse.getBody());
            throw new ApiErrorException("Status: " + sessionCreationResponse.getStatus() +
                    "; message: " + sessionCreationResponse.getBody());
        }
        String locationValue = sessionCreationResponse.getHeaders().get("Location").get(0);
        String sessionKey = locationValue.substring(locationValue.lastIndexOf('/') + 1);
        log.info("Sending request to Rapid API endpoint");
        HttpResponse<String> response = Unirest.get(GET_ENDPOINT + sessionKey)
                .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                .asString();
        log.info("Rapid API response status : " + response.getStatus());
        if (response.getStatus() < 300) {
            log.info("Received data from Rapid API endpoint");
            result = deserializer.deserializeFlightsData(response.getBody());
        } else {
            log.error("Rapid API response error : " + response.getBody());
            throw new ApiErrorException(response.getBody());
        }
        log.info("Tickets list ready");

        return result;
    }
}
