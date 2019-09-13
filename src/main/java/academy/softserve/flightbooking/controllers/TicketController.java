package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.models.SearchCriterion;
import academy.softserve.flightbooking.models.tickets.Ticket;
import academy.softserve.flightbooking.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class TicketController {
    private final TicketService ticketService;
    private static final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/flights")
    public ResponseEntity<List<TicketDTO>> getTickets(@RequestBody SearchCriterionDTO searchCriterionDTO) {
        SearchCriterion searchCriterion = modelMapper.map(searchCriterionDTO, SearchCriterion.class);
        List<Ticket> tickets = ticketService.getTickets(searchCriterion);
        TicketDTO ticketDTO;
        List<TicketDTO> ticketDTOs = Collections.EMPTY_LIST;
        for( Ticket ticket : tickets ) {
            ticketDTO = modelMapper.map(ticket, TicketDTO.class);
            ticketDTOs.add(ticketDTO);
        }
        return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
    }
}
