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
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
@Component
public class KiwiApiConnector {
    private final static String FLIGHTS_ENDPOINT = "https://api.skypicker.com/flights?";
    private final static String MULTICITY_ENDPOINT = "https://api.skypicker.com/flights_multi?";
    private SearchParamsIntoKiwiApiRequestStringConverter converter;
    private KiwiApiResponseDeserializer deserializer;

    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException {
        List<TicketDTO> result;

        String parameters = converter.convertIntoRequestString(searchCriterionDTO);
        HttpResponse<String> response = Unirest.get(FLIGHTS_ENDPOINT + parameters).asString();
        if (response.getStatus() < 300) {
            result = deserializer.deserializeFlightsData(response.getBody());
        } else {
            throw new ApiErrorException(response.getBody());
        }

        return result;
    }
}
