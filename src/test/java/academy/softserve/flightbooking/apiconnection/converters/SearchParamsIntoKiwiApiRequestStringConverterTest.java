package academy.softserve.flightbooking.apiconnection.converters;


import academy.softserve.flightbooking.apiconnection.converters.ParametersStringBuilder;
import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoKiwiApiRequestStringConverter;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.exceptions.IllegalDateException;
import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static academy.softserve.flightbooking.models.components.CabinClass.ECONOMY;
import static academy.softserve.flightbooking.models.components.TicketType.ROUNDTRIP;


@Slf4j
@SpringBootTest
public class SearchParamsIntoKiwiApiRequestStringConverterTest {

    @Test
    public void convertIntoRequestStringRoundtripSuccess() throws UnsupportedEncodingException, IllegalDateException, IllegalCabinClassException {
        //Given
        SearchCriterionDTO searchCriterion = new SearchCriterionDTO();
        searchCriterion.setId(1L);
        searchCriterion.setCurrencyCode("USD");
        searchCriterion.setTicketType(ROUNDTRIP);
        searchCriterion.setCabinClass(ECONOMY);
        searchCriterion.setAdults(1);
        searchCriterion.setChildren(0);
        searchCriterion.setFromLocation("OZH");
        searchCriterion.setToLocation("KBP");
        searchCriterion.setDepartDate(1576620000000L);
        searchCriterion.setReturnDate(1576620000000L);

        String expected = "adults=1&children=0&currency=USD&date_from=18%2F12%2F2019&date_to=18%2F12%2F2019&fly_from=OZH&fly_to=KBP&max_stopovers=2147483647&partner=picky&return_from=18%2F12%2F2019&return_to=18%2F12%2F2019&selected_cabins=M&v=3";
        log.info("expected : " + expected);

        //When
        SearchParamsIntoKiwiApiRequestStringConverter converter = new SearchParamsIntoKiwiApiRequestStringConverter(new ParametersStringBuilder());
        String actual = converter.convertIntoRequestString(searchCriterion);
        log.info("actual : " + actual);

        //Then
        Assert.assertEquals(expected, actual);
    }


}
