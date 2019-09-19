package academy.softserve.flightbooking.apiconnection;


import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoKiwiApiRequestStringConverter;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static academy.softserve.flightbooking.models.components.CabinClass.ECONOMY;
import static academy.softserve.flightbooking.models.components.TicketType.ONEWAY;


@SpringBootTest
public class SearchParamsIntoKiwiApiRequestStringConverterTest {

    @Test
    public void convertIntoRequestStringSuccess() throws UnsupportedEncodingException, IllegalDateException, IllegalCabinClassException {
        //Given
        SearchCriterionDTO searchCriterion = new SearchCriterionDTO();
        searchCriterion.setId(1L);
        searchCriterion.setCurrencyCode("USD");
        searchCriterion.setTicketType(ONEWAY);
        searchCriterion.setCabinClass(ECONOMY);
        searchCriterion.setAdults(1);
        searchCriterion.setChildren(0);
        searchCriterion.setFromLocation("OZH");
        searchCriterion.setToLocation("KBP");
        searchCriterion.setDepartDate(1576620000000L);
        searchCriterion.setReturnDate(1576620000000L);

        String expected = "currency=USD&max_stopovers=2147483647&selected_cabins=M&adults=1&children=0&fly_from=OZH&fly_to=KBP&date_from=18%2F12%2F2019&date_to=18%2F12%2F2019&return_from=18%2F12%2F2019&return_to=18%2F12%2F2019&partner=picky&v=3";

        //When
        SearchParamsIntoKiwiApiRequestStringConverter converter = new SearchParamsIntoKiwiApiRequestStringConverter(new ParametersStringBuilder());
        String actual = converter.convertIntoRequestString(searchCriterion);

        //Then
        assert(actual.equals(expected));
    }
}
