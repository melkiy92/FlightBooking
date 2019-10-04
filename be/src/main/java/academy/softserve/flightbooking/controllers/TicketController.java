package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.dto.MultiCitySearchCriterionDTO;
import academy.softserve.flightbooking.exceptions.ApiErrorException;
import academy.softserve.flightbooking.exceptions.DeserializationException;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.exceptions.InvalidResponseJsonException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.exceptions.RequestException;
import academy.softserve.flightbooking.exceptions.ResponseException;
import academy.softserve.flightbooking.services.TicketService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketController {
    private final TicketService ticketService;
    private static final ModelMapper modelMapper = new ModelMapper();

    /*
    * DON'T DELETE COMMENTED CODE
    */
    @PostMapping("/flights")
    public ResponseEntity<List<TicketDTO>> getTickets(@RequestBody SearchCriterionDTO searchCriterionDTO)
            throws ResponseException, RequestException, NoTicketsException {
        //SearchCriterion searchCriterion = modelMapper.map(searchCriterionDTO, SearchCriterion.class);
        log.info("Received search criteria from UI : " + searchCriterionDTO.toString());
        List<TicketDTO> tickets = ticketService.getTickets(searchCriterionDTO);
        log.info("Received tickets list from service");
        /*TicketDTO ticketDTO;
        List<TicketDTO> ticketDTOs = Collections.EMPTY_LIST;
        for( Ticket ticket : tickets ) {
            ticketDTO = modelMapper.map(ticket, TicketDTO.class);
            ticketDTOs.add(ticketDTO);
        }*/
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PostMapping("/flights/multi")
    public ResponseEntity<List<TicketDTO>> getMultiCityTickets(@RequestBody MultiCitySearchCriterionDTO multiCitySearchCriterionDTO)
            throws ResponseException, RequestException, NoTicketsException {
        log.info("Received search criteria from UI : " + multiCitySearchCriterionDTO.toString());
        List<TicketDTO> tickets = ticketService.getMultiCityTickets(multiCitySearchCriterionDTO);
        log.info("Received tickets list from service");

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
