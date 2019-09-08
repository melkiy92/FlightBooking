package academy.softserve.flightbooking.apiconnection;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static academy.softserve.flightbooking.apiconnection.ParametersStringBuilder.getParamsString;

@Component
public class KiwiApiConnector {

    private SearchParamsIntoKiwiApiConverter converter;

    public KiwiApiConnector(SearchParamsIntoKiwiApiConverter converter) {
        this.converter = converter;
    }

    public String getFlightData(Map<String, String> searchParameters) throws UnirestException, UnsupportedEncodingException {

        Map<String, String> parameters = converter.convertIntoRequest(searchParameters);

        HttpResponse<String> response = Unirest.get("https://api.skypicker.com/flights?" + getParamsString(parameters))
                .asString();

        return response.getBody();
    }
}
