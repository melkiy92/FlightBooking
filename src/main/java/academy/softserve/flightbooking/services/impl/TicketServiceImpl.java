package academy.softserve.flightbooking.services.impl;

import academy.softserve.flightbooking.apiconnection.connectors.KiwiApiConnector;
import academy.softserve.flightbooking.apiconnection.connectors.RapidApiConnector;
import academy.softserve.flightbooking.apiconnection.exceptions.ApiErrorException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.services.TicketService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {
    private KiwiApiConnector kiwiApiConnector;
    private RapidApiConnector rapidApiConnector;

    @Override
    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO)
            throws IllegalCabinClassException, ApiErrorException, UnirestException,
                IllegalDateException, IOException {
        List<TicketDTO> tickets = new ArrayList<>();
        tickets.addAll(kiwiApiConnector.getTickets(searchCriterionDTO));
        log.info("Received tickets list from Kiwi Api Connector");
        tickets.addAll(rapidApiConnector.getTickets(searchCriterionDTO));
        log.info("Received tickets list from Rapid Api Connector");

        return tickets;
        }

}
