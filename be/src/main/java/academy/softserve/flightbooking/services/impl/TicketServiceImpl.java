package academy.softserve.flightbooking.services.impl;

import academy.softserve.flightbooking.apiconnection.connectors.KiwiApiConnector;
import academy.softserve.flightbooking.apiconnection.connectors.RapidApiConnector;
import academy.softserve.flightbooking.dto.MultiCitySearchCriterionDTO;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.*;
import academy.softserve.flightbooking.services.TicketService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
    public Page<TicketDTO> getTicketsPage(SearchCriterionDTO searchCriterionDTO, Pageable pageable)
            throws RequestException, ResponseException, NoTicketsException {
        List<TicketDTO> tickets = getTickets(searchCriterionDTO);
        log.info("Received tickets list from service");
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tickets.size());

        return new PageImpl<>(tickets.subList(start, end), pageable, tickets.size());
    }

    @Override
    public Page<TicketDTO> getMultiCityTicketsPage(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO, Pageable pageable)
         throws RequestException, ResponseException, NoTicketsException {
        List<TicketDTO> tickets = getMultiCityTickets(multiCitySearchCriterionDTO);
        log.info("Received tickets list from service");
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tickets.size());

        return new PageImpl<>(tickets.subList(start, end), pageable, tickets.size());
    }


    @Override
    public List<TicketDTO> getTickets(SearchCriterionDTO searchCriterionDTO) throws RequestException, ResponseException, NoTicketsException {
        List<TicketDTO> tickets = new ArrayList<>();
        boolean success = false;
        String requestExceptionMessage = null;
        String responseExceptionMessage = null;
        String noTicketsMessage = null;

        try {
            tickets.addAll(kiwiApiConnector.getTickets(searchCriterionDTO));
            log.info("Received tickets list from Kiwi Api Connector");
            success = true;
        } catch (NoTicketsException e) {
            log.info("No tickets available");
            responseExceptionMessage = "Kiwi API : " + e.getMessage();
        } catch (UnsupportedEncodingException | IllegalDateException | IllegalCabinClassException e) {
            log.error("Unable to get response due to bad request parameters : " + e.getMessage());
            requestExceptionMessage = "Unable to send request to Kiwi API server due to bad request parameters : " + e.getMessage();
        } catch (DeserializationException | ApiErrorException | UnirestException e) {
            log.error("Aggregator server error : " + e.getMessage());
            responseExceptionMessage = "Kiwi API server error : " + e.getMessage();
        }
        try {
            tickets.addAll(rapidApiConnector.getTickets(searchCriterionDTO));
            log.info("Received tickets list from Rapid Api Connector");
            success = true;
        } catch (NoTicketsException e) {
            log.info("No tickets available");
            responseExceptionMessage = responseExceptionMessage + " | Rapid API : " + e.getMessage();
        } catch (UnsupportedEncodingException | IllegalDateException | IllegalCabinClassException e) {
            log.error("Unable to get response due to bad request parameters : " + e.getMessage());
            requestExceptionMessage = requestExceptionMessage + " | Unable to send request to Rapid API server due to bad request parameters : " + e.getMessage();
        } catch (DeserializationException | ApiErrorException | UnirestException e) {
            log.error("Aggregator server error : " + e.getMessage());
            responseExceptionMessage = responseExceptionMessage + " | Rapid API server error : " + e.getMessage();
        }
        log.info("success=" + success);
        if (!success) {
            if (noTicketsMessage != null) {
                throw new NoTicketsException("No tickets available");
            } else if (requestExceptionMessage != null) {
                throw new RequestException(requestExceptionMessage);
            } else if (responseExceptionMessage != null) {
                throw new ResponseException(responseExceptionMessage);
            }
        }

        return tickets;
    }

    @Override
    public List<TicketDTO> getMultiCityTickets(MultiCitySearchCriterionDTO multiCitySearchCriterionDTO) throws NoTicketsException, RequestException, ResponseException {
        List<TicketDTO> tickets = new ArrayList<>();
        boolean success = false;
        String requestExceptionMessage = null;
        String responseExceptionMessage = null;
        String noTicketsMessage = null;

        try {
            tickets.addAll(kiwiApiConnector.getMultiCityTickets(multiCitySearchCriterionDTO));
            log.info("Received tickets list from Kiwi Api Connector");
            success = true;
        } catch (NoTicketsException e) {
            log.info("No tickets available");
            noTicketsMessage = "Kiwi API : " + e.getMessage();
        } catch (UnsupportedEncodingException | IllegalDateException | IllegalCabinClassException e) {
            log.error("Unable to get response due to bad request parameters : " + e.getMessage());
            requestExceptionMessage = "Unable to send request to Kiwi API server due to bad request parameters : " + e.getMessage();
        } catch (DeserializationException | ApiErrorException | UnirestException e) {
            log.error("Aggregator server error : " + e.getMessage());
            responseExceptionMessage = "Kiwi API server error : " + e.getMessage();
        }

        log.info("success=" + success);
        if (!success) {
            if (noTicketsMessage != null) {
                throw new NoTicketsException("No tickets available");
            } else if (requestExceptionMessage != null) {
                throw new RequestException(requestExceptionMessage);
            } else if (responseExceptionMessage != null) {
                throw new ResponseException(responseExceptionMessage);
            }
        }

        return tickets;
    }
}
