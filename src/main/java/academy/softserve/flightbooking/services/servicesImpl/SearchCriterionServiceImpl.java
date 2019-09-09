package academy.softserve.flightbooking.services.servicesImpl;

import academy.softserve.flightbooking.models.SearchCriterion;
import academy.softserve.flightbooking.repositories.SearchCriterionRepository;
import academy.softserve.flightbooking.services.SearchCriterionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCriterionServiceImpl implements SearchCriterionService {
    @NonNull
    private final SearchCriterionRepository searchCriterionRepository;

    @Override
    public SearchCriterion createSearchCriterion(){
        return null;
    }
}
