package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.exceptions.NoTicketsException;
import academy.softserve.flightbooking.exceptions.RequestException;
import academy.softserve.flightbooking.exceptions.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TicketControllerAdvice {
    @ResponseBody
    @ExceptionHandler(RequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String requestExceptionHandler(RequestException e) { return e.getMessage(); }

    @ResponseBody
    @ExceptionHandler(ResponseException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public String responseExceptionHandler(ResponseException e) { return e.getMessage(); }

    @ResponseBody
    @ExceptionHandler(NoTicketsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noTicketsExceptionHandler(NoTicketsException e) { return e.getMessage(); }
}

