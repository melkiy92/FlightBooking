package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.dto.SearchCriterionDTO;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static academy.softserve.flightbooking.models.components.CabinClass.ECONOMY;
import static academy.softserve.flightbooking.models.components.TicketType.ONEWAY;


@SpringBootTest
public class SearchParamsIntoRapidApiRequestConverterTest {

    @Test
    public void convertIntoRequestString() throws UnsupportedEncodingException, IllegalDateException, IllegalCabinClassException {
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

        String expected = "country=US&currency=USD&locale=en-US&originPlace=OZH-sky&destinationPlace=KBP-sky&outboundDate=2019-12-18&adults=1&inboundDate=2019-12-18&cabinClass=ECONOMY&children=0";

        //When
        SearchParamsIntoRapidApiRequestConverter converter =
                new SearchParamsIntoRapidApiRequestConverter(new ParametersStringBuilder());
        String actual = converter.convertIntoRequestString(searchCriterion);

        //Then
        assert(actual.equals(expected));

    }
}
