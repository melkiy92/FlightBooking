package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static academy.softserve.flightbooking.apiconnection.ParametersStringBuilder.getParamsString;

@Component
public class KiwiApiConnector {

    private SearchParamsIntoKiwiApiConverter converter;
    private KiwiResponseDeserializer deserializer;

    public KiwiApiConnector(SearchParamsIntoKiwiApiConverter converter, KiwiResponseDeserializer deserializer) {
        this.converter = converter;
        this.deserializer = deserializer;
    }

    public String getFlightData(Map<String, String> searchParameters) throws UnirestException, IOException {

        Map<String, String> parameters = converter.convertIntoRequest(searchParameters);

        System.out.println(getParamsString(parameters));

        HttpResponse<String> response = Unirest.get("https://api.skypicker.com/flights?" + getParamsString(parameters))
                .asString();

        System.out.println("response.getBody=");
        System.out.println(response.getBody());





        return deserializer.deserialize(response.getBody());
    }
}
