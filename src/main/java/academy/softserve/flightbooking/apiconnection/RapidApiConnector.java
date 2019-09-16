package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class RapidApiConnector {
    private static final String POST_ENDPOINT = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0/";
    private static final String GET_ENDPOINT = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/";
    private static final String X_RAPIDAPI_HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com" ;
    private static final String X_RAPIDAPI_KEY = "5ccd3b53abmshb992db8efa8fccep18a986jsn88569fa32f67";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";


    @Autowired
    private SearchParamsIntoApiRequestConverter converter;

    public String getFlightsData(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException {
        List<TicketDTO> result;

        System.out.println(converter);
        String parameters = converter.convertIntoRapidRequestString(searchCriterionDTO);

        System.out.println("start connection");

        HttpResponse<JsonNode> sessionCreationResponse = Unirest.post(POST_ENDPOINT)
                .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                .header("Content-Type", CONTENT_TYPE)
                .body(parameters)
                .asJson();

        System.out.println(sessionCreationResponse.getStatus() >= 300);

        if (sessionCreationResponse.getStatus() >= 300) {
            throw new ApiErrorException("Status: " + sessionCreationResponse.getStatus() +
                    "; message: " + sessionCreationResponse.getBody());
        }

        String locationValue = sessionCreationResponse.getHeaders().get("Location").get(0);
        String sessionKey = locationValue.substring(locationValue.lastIndexOf('/') + 1);

        System.out.println(GET_ENDPOINT + sessionKey);
        System.out.println("test=https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/");


        HttpResponse<String> response = Unirest.get(GET_ENDPOINT + sessionKey)
                .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                .asString();

        return response.getBody();
    }
}
