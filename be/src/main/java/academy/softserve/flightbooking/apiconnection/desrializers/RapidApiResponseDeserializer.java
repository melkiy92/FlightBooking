package academy.softserve.flightbooking.apiconnection.desrializers;

import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.exceptions.DeserializationException;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.MissingNodeException;
import academy.softserve.flightbooking.constants.ApiConnectionConstants;
import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.RouteDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.models.components.TicketType;
import academy.softserve.flightbooking.services.FlightStopsCalculationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static academy.softserve.flightbooking.apiconnection.converters.CabinClassConverter.convertStringIntoCabinClass;

@Slf4j
@Data
@AllArgsConstructor
@Component
public class RapidApiResponseDeserializer {
    private FlightStopsCalculationService flightStopsCalculationService;


    public List<TicketDTO> deserializeFlightsData(String json, TicketType ticketType)
            throws DeserializationException, NoTicketsException {
        List<TicketDTO> tickets = new ArrayList<>();
        try {
            Map<String, JsonNode> nodes = parseJson(json);
            log.info("Start deserialization");
            for(JsonNode itinerary : nodes.get("itineraries")) {
                try {
                    TicketDTO ticket = parseTicket(nodes, itinerary, ticketType);
                    tickets.add(ticket);
                } catch (ParseException | IllegalCabinClassException e) {
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

    private Map<String, JsonNode> parseJson(String json) throws IOException {
        Map<String, JsonNode> result = new HashMap<>();

        JsonNode data = new ObjectMapper().readTree(json);
        try {
            JsonNode itineraries = extractNodeFromNodeByNodeName(data, "Itineraries");
            result.put("itineraries", itineraries);
            JsonNode legs = extractNodeFromNodeByNodeName(data,"Legs");
            result.put("legs", legs);
            JsonNode segments = extractNodeFromNodeByNodeName(data, "Segments");
            result.put("segments", segments);
            List<JsonNode> carriersList = extractNodesFromNodeByNodeNames(data, "Carriers");
            JsonNode carriers = data.findValues("Carriers").get(carriersList.size() - 1);
            result.put("carriers", carriers);
            JsonNode places = extractNodeFromNodeByNodeName(data, "Places");
            result.put("places", places);
            JsonNode query = extractNodeFromNodeByNodeName(data, "Query");
            result.put("query", query);
        } catch (MissingNodeException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    private JsonNode extractNodeFromNodeByNodeName(JsonNode originalNode, String extractedNodeName) throws MissingNodeException {
        JsonNode result;

        result = originalNode.findValue(extractedNodeName);
        if(result == null) {
            throw new MissingNodeException("Missing node : " + extractedNodeName);
        }

        return result;
    }

    private List<JsonNode> extractNodesFromNodeByNodeNames(JsonNode originalNode, String extractedNodeName) throws MissingNodeException {
        List<JsonNode> result;

        result = originalNode.findValues(extractedNodeName);
        if(result == null) {
            throw new MissingNodeException("Missing node : " + extractedNodeName);
        }

        return result;
    }

    private TicketDTO parseTicket(Map<String, JsonNode> nodes, JsonNode itinerary, TicketType ticketType) throws ParseException, IllegalCabinClassException {
        TicketDTO ticket = new TicketDTO();
        List<RouteDTO> routs = new ArrayList<>();

        ticket.setPrice(itinerary.findValue("Price").asDouble());
        ticket.setBookingToken(itinerary.findValue("DeeplinkUrl").asText());
        ticket.setProvider("Skyscanner");
        String outboundLegId = itinerary.findValue("OutboundLegId").asText();
        JsonNode outboundLeg = findNodeInNodesById(nodes.get("legs"), outboundLegId);
        RouteDTO straightRoute = parseRout(nodes, outboundLeg);
        routs.add(straightRoute);
        if(ticketType.equals(TicketType.ROUNDTRIP)) {
            String inboundLegId = itinerary.findValue("InboundLegId").asText();
            JsonNode inboundLeg = findNodeInNodesById(nodes.get("legs"), inboundLegId);
            RouteDTO returnRoute = parseRout(nodes, inboundLeg);
            routs.add(returnRoute);
        }
        ticket.setRoutes(routs);

        return ticket;
    }

    private RouteDTO parseRout(Map<String, JsonNode> nodes, JsonNode leg) throws ParseException, IllegalCabinClassException {
        RouteDTO route = new RouteDTO();

        String originStationName = leg.findValue("OriginStation").asText();
        JsonNode originStation = findNodeInNodesById(nodes.get("places"), originStationName);
        route.setCityNameFrom(extractStringValueFromNode(originStation, "Name"));
        String destinationStationName = leg.findValue("DestinationStation").asText();
        JsonNode destinationStation = findNodeInNodesById(nodes.get("places"), destinationStationName);
        route.setCityNameTo(extractStringValueFromNode(destinationStation, "Name"));
        route.setDuration(leg.findValue("Duration").asLong());

        List<FlightDTO> flights = new ArrayList<>();
        JsonNode segmentsIds = leg.findValue("SegmentIds");
        for (JsonNode segmentId : segmentsIds) {
            JsonNode segment = findNodeInNodesById(nodes.get("segments"), segmentId.asText());
            FlightDTO flight = parseFlight(nodes, segment);
            flights.add(flight);
        }
        route.setFlights(flights);
        route.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(flights));

        return route;
    }

    private FlightDTO parseFlight(Map<String, JsonNode> nodes, JsonNode segment) throws ParseException, IllegalCabinClassException {
        FlightDTO flight = new FlightDTO();

        flight.setFlightNumber(segment.findValue("FlightNumber").asText());
        String departureDateTime = segment.findValue("DepartureDateTime").asText();
        String arrivalDateTime = segment.findValue("ArrivalDateTime").asText();
        flight.setDuration(segment.findValue("Duration").asLong());
        flight.setDepartTime(dateInMsFromDateString(departureDateTime, ApiConnectionConstants.RAPID_RESPONSE_JSON_DATE_TIME_PATTERN));
        flight.setArrivalTime(dateInMsFromDateString(arrivalDateTime, ApiConnectionConstants.RAPID_RESPONSE_JSON_DATE_TIME_PATTERN));
        flight.setArrivalCityName(extractValueFromNodeByKeyFromAnotherNodeAsString(nodes.get("places"), "Name", segment, "DestinationStation"));
        flight.setDepartCityName(extractValueFromNodeByKeyFromAnotherNodeAsString(nodes.get("places"), "Name", segment, "OriginStation"));
        flight.setArrivalAirportCode(extractValueFromNodeByKeyFromAnotherNodeAsString(nodes.get("places"), "Code", segment, "DestinationStation"));
        flight.setDepartAirportCode(extractValueFromNodeByKeyFromAnotherNodeAsString(nodes.get("places"), "Code", segment, "OriginStation"));
        flight.setAirlineName(extractValueFromNodeByKeyFromAnotherNodeAsString(nodes.get("carriers"), "Name", segment, "Carrier"));
        flight.setCabinClass(convertStringIntoCabinClass(extractStringValueFromNode(nodes.get("query"), "CabinClass"), Providers.RAPID));

        return flight;
    }

    private String extractStringValueFromNode (JsonNode jsonNode, String valueName) {
        String result = null;

        JsonNode resultAsNode = jsonNode.findValue(valueName);
        if(resultAsNode != null) {
            result = resultAsNode.asText();
        }

        return result;
    }

    private String extractValueFromNodeByKeyFromAnotherNodeAsString(JsonNode jsonNodeWithValue, String valueName, JsonNode jsonNodeWithKey, String jsonNodeKeyName) {
        String result = null;

        String id = jsonNodeWithKey.findValue(jsonNodeKeyName).asText();
        JsonNode node = findNodeInNodesById(jsonNodeWithValue, id);
        if(node != null) {
            node.findValue(valueName).asText();
        }

        return result;
    }

    private JsonNode findNodeInNodesById(JsonNode nodes, String id) {
        JsonNode result = null;

        for (JsonNode node : nodes) {
            if (node.findValue("Id").asText().equals(id)) {
                result = node;
            }
        }

        return result;
    }

    private Long dateInMsFromDateString (String dateInString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateInString).getTime();
    }
}
