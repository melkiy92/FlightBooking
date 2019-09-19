package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.apiconnection.converters.ParametersStringBuilder;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ParametersStringBuilderTest {

    @Test
    public void getParamsStringSuccess() throws UnsupportedEncodingException {
        //Given
        Map<String, String> params = new HashMap();
        params.put("currency", "USD");
        params.put("adults", "1");
        params.put("departure_date", "18/11/2019");

        String expected = "adults=1&departure_date=18%2F11%2F2019&currency=USD";

        //When
        ParametersStringBuilder parametersStringBuilder = new ParametersStringBuilder();
        String actual = parametersStringBuilder.getParamsString(params);

        //Then
        assert(actual.equals(expected));
    }
}
