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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:3000" })
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class TicketController {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    private final TicketService ticketService;
    private final TicketRepository ticketRepo;

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
    public ResponseEntity<Page<TicketDTO>> getTickets(@RequestBody SearchCriterionDTO searchCriterionDTO,
                                                      @PageableDefault(page = 0, size = 2)
                                                      @SortDefault.SortDefaults({
                                                              @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                                                              @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                      }) Pageable pageable)
            throws ResponseException, RequestException, NoTicketsException {
        log.info("Received search criteria from UI : " + searchCriterionDTO.toString());

        Page<TicketDTO> page = ticketService.getTicketsPage(searchCriterionDTO, pageable);
        log.info("Received page of tickets from service");

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/flights/multi")
    public ResponseEntity<Page<TicketDTO>> getMultiCityTickets(@RequestBody MultiCitySearchCriterionDTO multiCitySearchCriterionDTO,
                                                               @PageableDefault(page = 0, size = 2)
                                                               @SortDefault.SortDefaults({
                                                                       @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                                                                       @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                               }) Pageable pageable)
            throws ResponseException, RequestException, NoTicketsException {
        log.info("Received search criteria from UI : " + multiCitySearchCriterionDTO.toString());
        Page<TicketDTO> page = ticketService.getMultiCityTicketsPage(multiCitySearchCriterionDTO, pageable);
        log.info("Received page of multiCity tickets from service");

        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
