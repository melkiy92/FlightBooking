package academy.softserve.flightbooking.apiconnection.desrializers;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.RouteDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.services.FlightStopsCalculationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Component
public class RapidApiResponseDeserializer {
    private FlightStopsCalculationService flightStopsCalculationService;

    public List<TicketDTO> deserializeFlightsData(String json) throws IOException {
        Map<String, JsonNode> nodes = parseJson(json);
        List<TicketDTO> tickets = new ArrayList<>();

        for(JsonNode itinerary : nodes.get("itineraries")) {
            TicketDTO ticket = parseTicket(nodes, itinerary);
            tickets.add(ticket);
        }

        return tickets;
    }

    private Map<String, JsonNode> parseJson(String json) throws IOException {
        Map<String, JsonNode> result = new HashMap<>();

        JsonNode data = new ObjectMapper().readTree(json);
        JsonNode itineraries = data.findValue("Itineraries");
        result.put("itineraries", itineraries);
        JsonNode legs = data.findValue("Legs");
        result.put("legs", legs);
        JsonNode segments = data.findValue("Segments");
        result.put("segments", segments);
        List<JsonNode> carriersList = data.findValues("Carriers");
        JsonNode carriers = data.findValues("Carriers").get(carriersList.size() - 1);
        result.put("carriers", carriers);
        JsonNode places = data.findValue("Places");
        result.put("places", places);

        return result;
    }

    private TicketDTO parseTicket(Map<String, JsonNode> nodes, JsonNode itinerary) {
        TicketDTO ticket = new TicketDTO();
        List<RouteDTO> routs = new ArrayList<>();

        ticket.setPrice(itinerary.findValue("Price").asDouble());
        ticket.setBookingToken(itinerary.findValue("DeeplinkUrl").asText());
        ticket.setProvider("Skyscanner");
        String outboundLegId = itinerary.findValue("OutboundLegId").asText();
        String inboundLegId = itinerary.findValue("InboundLegId").asText();
        JsonNode outboundLeg = findById(nodes.get("legs"), outboundLegId);
        JsonNode inboundLeg = findById(nodes.get("legs"), inboundLegId);
        RouteDTO straightRoute = parseRout(nodes, outboundLeg);
        RouteDTO returnRoute = parseRout(nodes, inboundLeg);
        routs.add(straightRoute);
        routs.add(returnRoute);
        ticket.setRoutes(routs);

        return ticket;
    }

    private RouteDTO parseRout(Map<String, JsonNode> nodes, JsonNode leg) {
        RouteDTO result = new RouteDTO();

        String originStationName = leg.findValue("OriginStation").asText();
        JsonNode originStation = findById(nodes.get("places"), originStationName);
        result.setCityNameFrom(originStation.findValue("Name").asText());
        String destinationStationName = leg.findValue("DestinationStation").asText();
        JsonNode destinationStation = findById(nodes.get("places"), destinationStationName);
        result.setCityNameTo(destinationStation.findValue("Name").asText());
        result.setDuration(leg.findValue("Duration").asLong());

        List<FlightDTO> flights = new ArrayList<>();
        JsonNode segmentsIds = leg.findValue("SegmentIds");
        for (JsonNode segmentId : segmentsIds) {
            JsonNode segment = findById(nodes.get("segments"), segmentId.asText());
            FlightDTO flight = parseFlight(nodes, segment);
            flights.add(flight);
        }
        result.setFlights(flights);
        result.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(flights));

        return result;
    }

    private FlightDTO parseFlight(Map<String, JsonNode> nodes, JsonNode segment) {
        FlightDTO flight = new FlightDTO();

        flight.setFlightNumber(segment.findValue("FlightNumber").asText());
        String departureDateTime = segment.findValue("DepartureDateTime").asText();
        String arrivalDateTime = segment.findValue("ArrivalDateTime").asText();
        flight.setDuration(segment.findValue("Duration").asLong());
        flight.setArrivalCityName(findById(nodes.get("places"), segment.findValue("DestinationStation").asText()).findValue("Name").asText());
        flight.setDepartCityName(findById(nodes.get("places"), segment.findValue("OriginStation").asText()).findValue("Name").asText());
        flight.setArrivalAirportCode(findById(nodes.get("places"), segment.findValue("DestinationStation").asText()).findValue("Code").asText());
        flight.setDepartAirportCode(findById(nodes.get("places"), segment.findValue("OriginStation").asText()).findValue("Code").asText());
        flight.setDepartTime(dateInMsFromDateString(departureDateTime, "yyyy-MM-dd'T'hh:mm:ss"));
        flight.setArrivalTime(dateInMsFromDateString(arrivalDateTime, "yyyy-MM-dd'T'hh:mm:ss"));
        flight.setAirlineName(findById(nodes.get("carriers"), segment.findValue("Carrier").asText()).findValue("Name").asText());

        return flight;
    }

    private JsonNode findById(JsonNode nodes, String id) {
        JsonNode result = null;

        for (JsonNode node : nodes) {
            if (node.findValue("Id").asText().equals(id)) {
                result = node;
            }
        }

        return result;
    }

    private Long dateInMsFromDateString (String dateInString, String pattern) {
        Date date;

        try {
            date = new SimpleDateFormat(pattern).parse(dateInString);
        } catch (ParseException e) {
            date = null;
        }

        return date.getTime();
    }
}
