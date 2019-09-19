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
import java.util.List;

@Data
@AllArgsConstructor
@Component
public class RapidApiResponseDeserializer {

    private FlightStopsCalculationService flightStopsCalculationService;

    public List<TicketDTO> deserializeFlightsData(String json) throws IOException {
        JsonNode data = new ObjectMapper().readTree(json);
        JsonNode itineraries = data.findValue("Itineraries");
        JsonNode legs = data.findValue("Legs");
        JsonNode segments = data.findValue("Segments");
        List<JsonNode> carriersList = data.findValues("Carriers");
        JsonNode carriers = carriersList.get(carriersList.size() - 1);
        List<JsonNode> agentsList = data.findValues("Agents");
        JsonNode agents = agentsList.get(agentsList.size() - 1);
        JsonNode places = data.findValue("Places");

        List<TicketDTO> tickets = new ArrayList<>();
        for(JsonNode itinerary : itineraries) {
            JsonNode outboundLeg = findById(legs, itinerary.findValue("OutboundLegId").asText());
            JsonNode outboundSegmentsIds = outboundLeg.findValue("SegmentIds");
            JsonNode originStation = findById(places, outboundLeg.findValue("OriginStation").asText());
            JsonNode destinationStation = findById(places, outboundLeg.findValue("DestinationStation").asText());
            JsonNode inboundLeg = findById(legs, itinerary.findValue("InboundLegId").asText());
            JsonNode inboundSegmentsIds = inboundLeg.findValue("SegmentIds");

            TicketDTO ticket = new TicketDTO();
            ticket.setPrice(itinerary.findValue("Price").asDouble());
            ticket.setBookingToken(itinerary.findValue("DeeplinkUrl").asText());
            ticket.setProvider("Skyscanner");
            List<RouteDTO> routs = new ArrayList<>();
            RouteDTO straightRoute = new RouteDTO();
            straightRoute.setDuration(outboundLeg.findValue("Duration").asLong());
            straightRoute.setCityNameFrom(originStation.findValue("Name").asText());
            straightRoute.setCityNameTo(destinationStation.findValue("Name").asText());
            List<FlightDTO> straightFlights = new ArrayList<>();
            for (JsonNode outboundSegmentId : outboundSegmentsIds) {
                JsonNode segment = findById(segments, outboundSegmentId.asText());
                FlightDTO flight = parseFlight(segment, places, carriers);
                straightFlights.add(flight);
            }
            straightRoute.setFlights(straightFlights);
            straightRoute.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(straightFlights));
            routs.add(straightRoute);

            RouteDTO returnRoute = new RouteDTO();
            returnRoute.setDuration(inboundLeg.findValue("Duration").asLong());
            returnRoute.setCityNameFrom(destinationStation.findValue("Name").asText());
            returnRoute.setCityNameTo(originStation.findValue("Name").asText());
            List<FlightDTO> returnFlights = new ArrayList<>();
            for (JsonNode inboundSegmentId : inboundSegmentsIds) {
                JsonNode segment = findById(segments, inboundSegmentId.asText());
                FlightDTO flight = parseFlight(segment, places, carriers);
                returnFlights.add(flight);
            }
            returnRoute.setFlights(returnFlights);
            returnRoute.setStops(flightStopsCalculationService.calculateStopsBetweenFlights(returnFlights));
            routs.add(returnRoute);

            ticket.setRoutes(routs);
            tickets.add(ticket);
        }

        return tickets;
    }

    private static JsonNode findById(JsonNode nodes, String id) {
        JsonNode result = null;
        for (JsonNode node : nodes) {
            if (node.findValue("Id").asText().equals(id)) {
                result = node;
            }
        }
        return result;
    }

    private static Long dateInMsFromDateString (String dateInString, String pattern) {
        Date date;
        try {
            date = new SimpleDateFormat(pattern).parse(dateInString);
        } catch (ParseException e) {
            date = null;
        }
        return date.getTime();
    }

    private static FlightDTO parseFlight(JsonNode segment, JsonNode places, JsonNode carriers) {
        FlightDTO flight = new FlightDTO();
        flight.setFlightNumber(segment.findValue("FlightNumber").asText());
        String departureDateTime = segment.findValue("DepartureDateTime").asText();
        String departureDateString = departureDateTime.substring(0, departureDateTime.indexOf("T"));
        flight.setDepartDate(dateInMsFromDateString(departureDateString, "yyyy-MM-dd"));
        String arrivalDateTime = segment.findValue("ArrivalDateTime").asText();
        String arrivalDateString = arrivalDateTime.substring(0, arrivalDateTime.indexOf("T"));
        //flight.setArrivalDate(dateInMsFromDateString(arrivalDateString, "yyyy-MM-dd"));
        flight.setDuration(segment.findValue("Duration").asLong());
        flight.setArrivalCityName(findById(places, segment.findValue("DestinationStation").asText()).findValue("Name").asText());
        flight.setDepartCityName(findById(places, segment.findValue("OriginStation").asText()).findValue("Name").asText());
        flight.setArrivalAirportCode(findById(places, segment.findValue("DestinationStation").asText()).findValue("Code").asText());
        flight.setDepartAirportCode(findById(places, segment.findValue("OriginStation").asText()).findValue("Code").asText());
        String departureTimeString = departureDateTime.substring(departureDateTime.indexOf("T") + 1);
        flight.setDepartTime(dateInMsFromDateString(departureTimeString, "hh:mm:ss"));
        String arrivalTimeString = arrivalDateTime.substring(arrivalDateTime.indexOf("T") + 1);
        flight.setArrivalTime(dateInMsFromDateString(arrivalTimeString, "hh:mm:ss"));
        flight.setAirlineName(findById(carriers, segment.findValue("Carrier").asText()).findValue("Name").asText());
        return flight;
    }
}
