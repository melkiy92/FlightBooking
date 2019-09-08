package academy.softserve.flightbooking.apiconnection;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static academy.softserve.flightbooking.apiconnection.ParametersStringBuilder.getParamsString;

@Component
public class RapidApiConnector {

    private SearchParamsIntoRapidApiConverter converter;

    public RapidApiConnector(SearchParamsIntoRapidApiConverter converter) {
        this.converter = converter;
    }

    public String getFlightData(Map<String, String> searchParameters) throws UnirestException, UnsupportedEncodingException {

        Map<String, String> parameters = converter.convertIntoRequest(searchParameters);

        System.out.println(getParamsString(parameters));

        HttpResponse<JsonNode> sessionCreationResponse = Unirest.post("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0")
                .header("X-RapidAPI-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "5ccd3b53abmshb992db8efa8fccep18a986jsn88569fa32f67")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(getParamsString(parameters))
                .asJson();

        if (sessionCreationResponse.getStatus() >= 300) {
            return "Status: " + sessionCreationResponse.getStatus() + "; message: " + sessionCreationResponse.getBody();
        }

        String locationValue = sessionCreationResponse.getHeaders().get("Location").get(0);
        String sessionKey = locationValue.substring(locationValue.lastIndexOf('/') + 1, locationValue.length());

        HttpResponse<String> response = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/" + sessionKey)
                .header("X-RapidAPI-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "5ccd3b53abmshb992db8efa8fccep18a986jsn88569fa32f67")
                .asString();

        return response.getBody();
    }
}

