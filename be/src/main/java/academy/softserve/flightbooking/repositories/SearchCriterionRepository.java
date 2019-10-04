package academy.softserve.flightbooking.repositories;

import academy.softserve.flightbooking.models.SearchCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchCriterionRepository extends JpaRepository<SearchCriterion, Long> {

}
