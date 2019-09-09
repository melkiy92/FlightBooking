package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.models.SearchCriterion;
import academy.softserve.flightbooking.models.tickets.Flight;
import academy.softserve.flightbooking.models.tickets.Ticket;
import academy.softserve.flightbooking.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
}
