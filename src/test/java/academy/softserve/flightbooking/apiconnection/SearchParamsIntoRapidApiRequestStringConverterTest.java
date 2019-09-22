package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.apiconnection.converters.ParametersStringBuilder;
import academy.softserve.flightbooking.apiconnection.converters.SearchParamsIntoRapidApiRequestStringConverter;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.apiconnection.exceptions.IllegalDateException;
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
public class SearchParamsIntoRapidApiRequestStringConverterTest {

    @Test
    public void convertIntoRequestString() throws UnsupportedEncodingException, IllegalDateException, IllegalCabinClassException {
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

        String expected = "adults=1&cabinClass=economy&children=0&country=US&currency=USD&destinationPlace=KBP-sky&inboundDate=2019-12-18&locale=en-US&originPlace=OZH-sky&outboundDate=2019-12-18";
        log.info("expected : " + expected);

        //When
        SearchParamsIntoRapidApiRequestStringConverter converter =
                new SearchParamsIntoRapidApiRequestStringConverter(new ParametersStringBuilder());
        String actual = converter.convertIntoRequestString(searchCriterion);
        log.info("actual : " + actual);

        //Then
        Assert.assertEquals(expected, actual);
    }
}
