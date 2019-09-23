package academy.softserve.flightbooking.services.impl;

import academy.softserve.flightbooking.apiconnection.connectors.KiwiApiConnector;
import academy.softserve.flightbooking.apiconnection.connectors.RapidApiConnector;
import academy.softserve.flightbooking.exceptions.ApiErrorException;
import academy.softserve.flightbooking.exceptions.DeserializationException;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.RequestException;
import academy.softserve.flightbooking.exceptions.ResponseException;
import academy.softserve.flightbooking.services.TicketService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {
    private KiwiApiConnector kiwiApiConnector;
    private RapidApiConnector rapidApiConnector;

    @Override
    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO) throws RequestException, ResponseException {
        List<TicketDTO> tickets = new ArrayList<>();
        boolean success = false;
        String requestExceptionMessage = null;
        String responseExceptionMessage = null;

        try {
            tickets.addAll(kiwiApiConnector.getTickets(searchCriterionDTO));
            log.info("Received tickets list from Kiwi Api Connector");
            success = true;
        } catch (UnsupportedEncodingException | IllegalDateException | IllegalCabinClassException e) {
            requestExceptionMessage = e.getMessage();
        } catch (DeserializationException | ApiErrorException | UnirestException e) {
            responseExceptionMessage = e.getMessage();
        }
        try {
        tickets.addAll(rapidApiConnector.getTickets(searchCriterionDTO));
        log.info("Received tickets list from Rapid Api Connector");
        } catch (UnsupportedEncodingException | IllegalDateException | IllegalCabinClassException e) {
            requestExceptionMessage = e.getMessage();
        } catch (DeserializationException | ApiErrorException | UnirestException e) {
            responseExceptionMessage = e.getMessage();
        }
        if(!success) {
            if(requestExceptionMessage != null) {
                throw new RequestException(requestExceptionMessage);
            } else if (responseExceptionMessage != null) {
                throw new ResponseException(responseExceptionMessage);
            }
        }

        return tickets;
    }
}
