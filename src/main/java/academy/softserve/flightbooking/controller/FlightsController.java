package academy.softserve.flightbooking.controller;

import academy.softserve.flightbooking.apiconnection.KiwiApiConnector;
import academy.softserve.flightbooking.apiconnection.RapidApiConnector;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FlightsController {

    private KiwiApiConnector kiwiApiConnector;
    private RapidApiConnector rapidApiConnector;
    private Map<String, String> testSearchParameters;

    private static Map<String, String> getMap() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("fly_from", "PRG");
        parameters.put("fly_to", "LGW");
        parameters.put("date_from", "18/11/2019");
        parameters.put("date_to", "18/12/2019");
        parameters.put("adults", "1");
        parameters.put("country", "US");
        parameters.put("currency", "USD");
        parameters.put("locale", "en-US");
        return parameters;
    }

    public FlightsController(KiwiApiConnector kiwiApiConnector, RapidApiConnector rapidApiConnector) {
        this.kiwiApiConnector = kiwiApiConnector;
        this.rapidApiConnector = rapidApiConnector;
        testSearchParameters = getMap();
    }

    @GetMapping("/test/kiwi")
    public String getKiwiData(Map<String, String> searchParameters) throws UnsupportedEncodingException, UnirestException {
        searchParameters = testSearchParameters;
        return kiwiApiConnector.getFlightData(searchParameters);
    }

    @GetMapping("/test/rapid")
    public String getRapidData(Map<String, String> searchParameters) throws UnirestException, UnsupportedEncodingException {
        searchParameters = testSearchParameters;
        return rapidApiConnector.getFlightData(searchParameters);
    }
}
