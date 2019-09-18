package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

import static academy.softserve.flightbooking.apiconnection.KiwiResponseDeserializer.deserializeFlightsData;

@Data
@AllArgsConstructor
@Component
public class KiwiApiConnector implements IApiConnector {
    private final static String FLIGHTS_ENDPOINT = "https://api.skypicker.com/flights?";
    private final static String MULTICITY_ENDPOINT = "https://api.skypicker.com/flights_multi?";
    private SearchParamsIntoKiwiApiRequestConverter converter;

    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws IOException, UnirestException, ApiErrorException, IllegalDateException, IllegalCabinClassException {
        List<TicketDTO> result;

        String parameters = converter.convertIntoRequestString(searchCriterionDTO);
        HttpResponse<String> response = Unirest.get(FLIGHTS_ENDPOINT + parameters).asString();
        if (response.getStatus() < 300) {
            result = deserializeFlightsData(response.getBody());
        } else {
            throw new ApiErrorException(response.getBody());
        }

        return result;
    }
}
