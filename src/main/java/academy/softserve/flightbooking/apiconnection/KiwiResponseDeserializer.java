package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.RouteDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static java.time.LocalDate.ofEpochDay;
import static java.time.LocalTime.ofNanoOfDay;

@Component
public class KiwiResponseDeserializer {

    public List<TicketDTO> deserialize(String json) throws IOException {
        List<TicketDTO> tickets = new ArrayList<>();

        JsonNode data = new ObjectMapper().readTree(json).get("data");
        for (JsonNode node: data) {
            TicketDTO ticket = new TicketDTO();
            ticket.setPrice(node.get("price").asDouble());
            ticket.setBookingToken(node.get("booking_token").asText());
            ticket.setProvider("Kiwi");
            List<RouteDTO> routes = new ArrayList<>();
            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setCityNameFrom(node.get("cityFrom").asText());
            routeDTO.setCityNameTo(node.get("cityTo").asText());

            routeDTO.setDuration(ofSeconds(node.findValue("duration").get("total").asLong()));

            JsonNode route = node.findValue("route");
            List<FlightDTO> flights = new ArrayList<>();
            for(JsonNode nodeRoute: route) {
                FlightDTO flight = new FlightDTO();
                flight.setFlightNumber(nodeRoute.get("flight_no").asText());
                flight.setDepartDate(ofEpochDay(nodeRoute.get("dTime").asLong()));
                flight.setAirlineName(nodeRoute.get("airline").asText());
                flight.setDepartTime(ofNanoOfDay(nodeRoute.get("dTime").asLong()));
                flight.setDepartAirportCode(nodeRoute.get("flyFrom").asText());
                flight.setDepartCityName(nodeRoute.get("cityFrom").asText());
                flight.setArrivalCityName(nodeRoute.get("cityFrom").asText());
                flight.setArrivalAirportCode(nodeRoute.get("flyTo").asText());
                flight.setArrivalCityName(nodeRoute.get("cityTo").asText());
                flight.setArrivalTime(ofNanoOfDay(nodeRoute.get("aTime").asLong()));
                flight.setDuration(ofSeconds((nodeRoute.get("aTime").asLong()) - (nodeRoute.get("dTime").asLong())));
                flights.add(flight);
            }
            routeDTO.setFlights(flights);
            routes.add(routeDTO);
            ticket.setRoutes(routes);
            tickets.add(ticket);
        }

        return tickets;
    }
}
