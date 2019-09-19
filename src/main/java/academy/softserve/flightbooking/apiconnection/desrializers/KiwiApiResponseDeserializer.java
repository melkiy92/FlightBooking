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

    private final static Integer STRAIGHT_FLIGHT_INDEX = 0;
    private final static Integer RETURN_FLIGHT_INDEX = 1;


    public List<TicketDTO> deserializeFlightsData(String json) throws IOException {
        JsonNode data = new ObjectMapper().readTree(json).get("data");
        List<TicketDTO> tickets = new ArrayList<>();

        for (JsonNode node: data) {
            TicketDTO ticket = parseTicket(node);
            tickets.add(ticket);
        }

        return tickets;
    }

    private TicketDTO parseTicket(JsonNode dataNode) {
        TicketDTO ticket = new TicketDTO();
        List<RouteDTO> routes = new ArrayList<>();

        ticket.setProvider("Kiwi");
        ticket.setPrice(dataNode.get("price").asDouble());
        ticket.setBookingToken(dataNode.get("deep_link").asText());

        JsonNode route = dataNode.findValue("route");
        RouteDTO straightRoute = parseRoute(route, STRAIGHT_FLIGHT_INDEX);
        RouteDTO returnRoute = parseRoute(route, RETURN_FLIGHT_INDEX);
        straightRoute.setDuration(dataNode.findValue("duration").get("departure").asLong());
        returnRoute.setDuration(dataNode.findValue("duration").get("return").asLong());
        routes.add(straightRoute);
        routes.add(returnRoute);
        ticket.setRoutes(routes);

        return ticket;
    }

    private RouteDTO parseRoute(JsonNode routeNode, Integer directionFlightIndex) {
        RouteDTO route = new RouteDTO();
        List<FlightDTO> flights = new ArrayList<>();

        for(JsonNode nodeRoute: routeNode) {
            if (nodeRoute.get("return").asInt() == directionFlightIndex) {
                FlightDTO flight = parseFlight(nodeRoute);
                flights.add(flight);
            }
        }
        route.setCityNameFrom(flights.get(0).getDepartCityName());
        route.setCityNameTo(flights.get(flights.size() - 1).getArrivalCityName());
        route.setFlights(flights);
        route.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(flights));

        return route;
    }

    private FlightDTO parseFlight(JsonNode nodeRoute) {
        FlightDTO flight = new FlightDTO();

        flight.setFlightNumber(nodeRoute.get("flight_no").asText());
        flight.setAirlineName(nodeRoute.get("airline").asText());
        flight.setDepartTime(nodeRoute.get("dTime").asLong());
        flight.setDepartAirportCode(nodeRoute.get("flyFrom").asText());
        flight.setDepartCityName(nodeRoute.get("cityFrom").asText());
        flight.setArrivalCityName(nodeRoute.get("cityFrom").asText());
        flight.setArrivalAirportCode(nodeRoute.get("flyTo").asText());
        flight.setArrivalCityName(nodeRoute.get("cityTo").asText());
        flight.setArrivalTime(nodeRoute.get("aTime").asLong());
        flight.setDuration((nodeRoute.get("aTime").asLong()) - (nodeRoute.get("dTime").asLong()));

        return flight;
    }
}
