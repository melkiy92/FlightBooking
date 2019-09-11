package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static academy.softserve.flightbooking.apiconnection.SearchParamsIntoKiwiApiConverter.convertIntoRequest;

@Component
public class KiwiApiConnector {
    private final static String FLIGHTS_ENDPOINT = "https://api.skypicker.com/flights?";

    private KiwiResponseDeserializer deserializer;

    public KiwiApiConnector(KiwiResponseDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    public List<TicketDTO> getFlightData(SearchCriterionDTO searchCriterionDTO) throws UnirestException, IOException {

        String parameters = convertIntoRequest(searchCriterionDTO);

        HttpResponse<String> response = Unirest.get(FLIGHTS_ENDPOINT + parameters)
                .asString();

        return deserializer.deserialize(response.getBody());
    }
}
