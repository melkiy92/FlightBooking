package academy.softserve.flightbooking.apiconnection.connectors;

import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoRapidApiRequestStringConverter;
import academy.softserve.flightbooking.apiconnection.desrializers.RapidApiResponseDeserializer;
import academy.softserve.flightbooking.exceptions.ApiErrorException;
import academy.softserve.flightbooking.exceptions.DeserializationException;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.constants.ApiConnectionConstants;
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


import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class RapidApiConnector {
    private SearchParamsIntoRapidApiRequestStringConverter converter;
    private RapidApiResponseDeserializer deserializer;


    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws ApiErrorException, DeserializationException, UnirestException, UnsupportedEncodingException, IllegalDateException, IllegalCabinClassException {
        List<TicketDTO> result;

        String parameters = converter.convertIntoRequestString(searchCriterionDTO);
        log.info("Creating session at Rapid API endpoint");

        HttpResponse<JsonNode> sessionCreationResponse = Unirest.post(ApiConnectionConstants.RAPID_POST_ENDPOINT)
                .header("X-RapidAPI-Host", ApiConnectionConstants.RAPID_X_RAPIDAPI_HOST)
                .header("X-RapidAPI-Key", ApiConnectionConstants.RAPID_X_RAPIDAPI_KEY)
                .header("Content-Type", ApiConnectionConstants.RAPID_CONTENT_TYPE)
                .body(parameters)
                .asJson();
        log.info("Rapid API session creation response status : " + sessionCreationResponse.getStatus());
        if (sessionCreationResponse.getStatus() < 300) {
            String locationValue = sessionCreationResponse.getHeaders().get("Location").get(0);
            String sessionKey = locationValue.substring(locationValue.lastIndexOf('/') + 1);
            log.info("Sending request to Rapid API endpoint");
            HttpResponse<String> response = Unirest.get(ApiConnectionConstants.RAPID_GET_ENDPOINT + sessionKey)
                    .header("X-RapidAPI-Host", ApiConnectionConstants.RAPID_X_RAPIDAPI_HOST)
                    .header("X-RapidAPI-Key", ApiConnectionConstants.RAPID_X_RAPIDAPI_KEY)
                    .asString();
            log.info("Rapid API response status : " + response.getStatus());
            if (response.getStatus() < 300) {
                log.info("Received data from Rapid API endpoint");

                    result = deserializer.deserializeFlightsData(response.getBody(), searchCriterionDTO.getTicketType());

            } else {
                log.error("Rapid API response error : " + response.getBody());
                throw new ApiErrorException(response.getBody());
            }
            log.info("Tickets list ready");
        } else {
            log.error("Session creation response error : " + sessionCreationResponse.getBody());
            throw new ApiErrorException("Status: " + sessionCreationResponse.getStatus() +
                    "; message: " + sessionCreationResponse.getBody());
        }

        return result;
    }
}
