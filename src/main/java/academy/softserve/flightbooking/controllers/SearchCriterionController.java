package academy.softserve.flightbooking.controllers;

import academy.softserve.flightbooking.services.SearchCriterionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchCriterionController {
    private final SearchCriterionService searchCriterionService;
}
