package academy.softserve.flightbooking.apiconnection.desrializers;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.RouteDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.services.FlightStopsCalculationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class KiwiApiResponseDeserializer {

    private FlightStopsCalculationService flightStopsCalculationService;

    public List<TicketDTO> deserializeFlightsData(String json) throws IOException {
        JsonNode data = new ObjectMapper().readTree(json).get("data");
        List<TicketDTO> tickets = new ArrayList<>();

        for (JsonNode node: data) {
            TicketDTO ticket = new TicketDTO();
            ticket.setPrice(node.get("price").asDouble());
            ticket.setBookingToken(node.get("booking_token").asText());
            ticket.setProvider("Kiwi");

            List<RouteDTO> routes = new ArrayList<>();
            RouteDTO straightRoute = new RouteDTO();
            RouteDTO returnRoute = new RouteDTO();
            JsonNode routesNode = node.findValue("routes");
            straightRoute.setCityNameFrom(routesNode.get(0).get(0).asText());
            straightRoute.setCityNameTo(routesNode.get(0).get(1).asText());
            straightRoute.setDuration(node.findValue("duration").get("departure").asLong());
            returnRoute.setCityNameFrom(routesNode.get(1).get(0).asText());
            returnRoute.setCityNameTo(routesNode.get(1).get(1).asText());
            returnRoute.setDuration(node.findValue("duration").get("return").asLong());

            JsonNode route = node.findValue("route");
            List<FlightDTO> straightFlights = new ArrayList<>();
            List<FlightDTO> returnFlights = new ArrayList<>();
            for(JsonNode nodeRoute: route) {
                FlightDTO flight = new FlightDTO();
                flight.setFlightNumber(nodeRoute.get("flight_no").asText());
                flight.setDepartDate(nodeRoute.get("dTime").asLong());
                flight.setAirlineName(nodeRoute.get("airline").asText());
                flight.setDepartTime(nodeRoute.get("dTime").asLong());
                flight.setDepartAirportCode(nodeRoute.get("flyFrom").asText());
                flight.setDepartCityName(nodeRoute.get("cityFrom").asText());
                flight.setArrivalCityName(nodeRoute.get("cityFrom").asText());
                flight.setArrivalAirportCode(nodeRoute.get("flyTo").asText());
                flight.setArrivalCityName(nodeRoute.get("cityTo").asText());
                flight.setArrivalTime(nodeRoute.get("aTime").asLong());
                flight.setDuration((nodeRoute.get("aTime").asLong()) - (nodeRoute.get("dTime").asLong()));
                if (nodeRoute.get("return").asInt() == 0) {
                    straightFlights.add(flight);
                } else {
                    returnFlights.add(flight);
                }
            }
            straightRoute.setFlights(straightFlights);
            straightRoute.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(straightFlights));
            returnRoute.setFlights(returnFlights);
            returnRoute.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(returnFlights));
            routes.add(straightRoute);
            routes.add(returnRoute);
            ticket.setRoutes(routes);
            tickets.add(ticket);
        }

        return tickets;
    }
}
