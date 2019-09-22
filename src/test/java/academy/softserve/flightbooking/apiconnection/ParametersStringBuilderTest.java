package academy.softserve.flightbooking.apiconnection;

import academy.softserve.flightbooking.apiconnection.converters.ParametersStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;;
import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
public class ParametersStringBuilderTest {

    @Test
    public void getParamsStringSuccess() throws UnsupportedEncodingException {
        //Given
        Map<String, String> params = new TreeMap();
        params.put("adults", "1");
        params.put("currency", "USD");
        params.put("departure_date", "18/11/2019");

        String expected = "adults=1&currency=USD&departure_date=18%2F11%2F2019";

        //When
        ParametersStringBuilder parametersStringBuilder = new ParametersStringBuilder();
        String actual = parametersStringBuilder.getParamsString(params);

        //Then
        Assert.assertEquals(expected, actual);
    }
}
