package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.KiwiResponseDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class KiwiResponseDeserializer {

    public String deserialize(String json) throws IOException {

        JsonNode data = new ObjectMapper().readTree(json).get("data");
        System.out.println("data=");
        System.out.println( data);

        return data.toString();
    }
}
