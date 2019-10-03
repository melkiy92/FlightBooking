package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.dto.MultiCitySearchCriterionDTO;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import academy.softserve.flightbooking.dto.TicketDTO;
import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.exceptions.RequestException;
import academy.softserve.flightbooking.exceptions.ResponseException;
import academy.softserve.flightbooking.repositories.TicketRepository;
import academy.softserve.flightbooking.services.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class TicketController {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    private final TicketService ticketService;
    private final TicketRepository ticketRepo;


//    @GetMapping(params = { "page", "size" })
//    public List<TicketDTO> findPaginated(@RequestParam("page") int page,
//                                   @RequestParam("size") int size, UriComponentsBuilder uriBuilder,
//                                   HttpServletResponse response) {
//        Page<TicketDTO> resultPage = service.findPaginated(page, size);
//        if (page > resultPage.getTotalPages()) {
//            throw new MyResourceNotFoundException();
//        }
//        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(
//                Foo.class, uriBuilder, response, page, resultPage.getTotalPages(), size));
//
//        return resultPage.getContent();
//    }

    /*
     * DON'T DELETE COMMENTED CODE
     */
//    @PostMapping("/flights")
//    public ResponseEntity<List<TicketDTO>> getTickets(@RequestBody SearchCriterionDTO searchCriterionDTO, Pageable pageable)
//            throws ResponseException, RequestException, NoTicketsException {
//        //SearchCriterion searchCriterion = modelMapper.map(searchCriterionDTO, SearchCriterion.class);
//        log.info("Received search criteria from UI : " + searchCriterionDTO.toString());
//        List<TicketDTO> tickets = ticketService.getTickets(searchCriterionDTO);
//        log.info("Received tickets list from service");
//        /*TicketDTO ticketDTO;
//        List<TicketDTO> ticketDTOs = Collections.EMPTY_LIST;
//        for( Ticket ticket : tickets ) {
//            ticketDTO = modelMapper.map(ticket, TicketDTO.class);
//            ticketDTOs.add(ticketDTO);
//        }*/
//        return new ResponseEntity<>(tickets, HttpStatus.OK);
//    }

    @PostMapping("/flights")
    public ResponseEntity<Page<TicketDTO>> getTickets(@RequestBody SearchCriterionDTO searchCriterionDTO, Pageable pageable)
            throws ResponseException, RequestException, NoTicketsException {
        log.info("Received search criteria from UI : " + searchCriterionDTO.toString());

        List<TicketDTO> tickets = ticketService.getTickets(searchCriterionDTO);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tickets.size());

        Page<TicketDTO> page = new PageImpl<>(tickets.subList(start, end), pageable, tickets.size());



        log.info("Received tickets list from service");
        return new ResponseEntity<>(page, HttpStatus.OK);
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
