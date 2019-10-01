package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.exceptions.RequestException;
import academy.softserve.flightbooking.exceptions.ResponseException;
import academy.softserve.flightbooking.services.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}