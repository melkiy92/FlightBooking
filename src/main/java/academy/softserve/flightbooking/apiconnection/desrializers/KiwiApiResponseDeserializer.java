package academy.softserve.flightbooking.apiconnection.desrializers;

import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.apiconnection.converters.CabinClassConverter;
import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.RouteDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.DeserializationException;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.models.components.TicketType;
import academy.softserve.flightbooking.services.FlightStopsCalculationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static academy.softserve.flightbooking.constants.ApiConnectionConstants.KIWI_RESPONSE_JSON_RETURN_FLIGHT_INDEX;
import static academy.softserve.flightbooking.constants.ApiConnectionConstants.KIWI_RESPONSE_JSON_STRAIGHT_FLIGHT_INDEX;


@Slf4j
@AllArgsConstructor
@Component
public class KiwiApiResponseDeserializer {
    private FlightStopsCalculationService flightStopsCalculationService;


    public List<TicketDTO> deserializeFlightsData(String json, TicketType ticketType) throws DeserializationException, NoTicketsException {
        List<TicketDTO> tickets = new ArrayList<>();
        log.info("ticket type : " + ticketType.toString());

        try {
            JsonNode data;
            if(ticketType.equals(TicketType.MULTICITY)) {
                log.info("json : " + json);
                data = new ObjectMapper().readTree(json);
            } else {
                data = new ObjectMapper().readTree(json).get("data");
            }
            log.info("data : " + data);
            log.info("Start deserialization");
            for (JsonNode node : data) {
                try {
                    TicketDTO ticket = parseTicket(node, ticketType);
                    tickets.add(ticket);
                } catch (IllegalCabinClassException e) {
                    log.error(e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error("Broken json response");
            throw new DeserializationException("Broken json response");
        }
        if(tickets.isEmpty()) {
            log.error("No tickets available");
            throw new NoTicketsException("No tickets available");
        }
        log.info("Deserialization successful");

        return tickets;
    }

    private TicketDTO parseTicket(JsonNode dataNode, TicketType ticketType) throws IllegalCabinClassException {
        TicketDTO ticket = new TicketDTO();
        List<RouteDTO> routes = new ArrayList<>();

        ticket.setProvider("Kiwi");
        ticket.setPrice(dataNode.get("price").asDouble());
        ticket.setBookingToken(dataNode.get("deep_link").asText());

        JsonNode route = dataNode.findValue("route");
        log.info("route : " + route);
        if(ticketType.equals(TicketType.MULTICITY)) {
            for(JsonNode n : route) {
                RouteDTO r = parseMultiCityRoute(n);
                r.setDuration(n.findValue("duration").get("departure").asLong());
                routes.add(r);
            }
        } else {
            RouteDTO straightRoute = parseRoute(route, KIWI_RESPONSE_JSON_STRAIGHT_FLIGHT_INDEX);
            straightRoute.setDuration(dataNode.findValue("duration").get("departure").asLong());
            routes.add(straightRoute);
            if (ticketType.equals(TicketType.ROUNDTRIP)) {
                RouteDTO returnRoute = parseRoute(route, KIWI_RESPONSE_JSON_RETURN_FLIGHT_INDEX);
                returnRoute.setDuration(dataNode.findValue("duration").get("return").asLong());
                routes.add(returnRoute);
            }
        }
        ticket.setRoutes(routes);

        return ticket;
    }

    private RouteDTO parseMultiCityRoute(JsonNode routeNode) throws IllegalCabinClassException {
        RouteDTO route = new RouteDTO();
        List<FlightDTO> flights = new ArrayList<>();

        log.info("node : " + routeNode);
        JsonNode inRoute = routeNode.findValue("route");
        log.info("inRout : " + inRoute);
        for(JsonNode n : inRoute) {
            FlightDTO flight = parseFlight(n);
            flights.add(flight);
        }
        route.setCityNameFrom(flights.get(0).getDepartCityName());
        route.setCityNameTo(flights.get(flights.size() - 1).getArrivalCityName());
        route.setFlights(flights);
        route.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(flights));

        return route;
    }

    private RouteDTO parseRoute(JsonNode routeNode, Integer directionFlightIndex) throws IllegalCabinClassException {
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

    private FlightDTO parseFlight(JsonNode nodeRoute) throws IllegalCabinClassException {
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
        flight.setCabinClass(CabinClassConverter.convertStringIntoCabinClass(nodeRoute.findValue("fare_category").asText(), Providers.KIWI));

        return flight;
    }
}
