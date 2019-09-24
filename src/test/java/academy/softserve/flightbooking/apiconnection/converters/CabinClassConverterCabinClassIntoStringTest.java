package academy.softserve.flightbooking.apiconnection.converters;


import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.models.components.CabinClass;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@Slf4j
@AllArgsConstructor
@SpringBootTest
@RunWith(Parameterized.class)
public class CabinClassConverterCabinClassIntoStringTest {
    private CabinClass input;
    private String expected;
    private Providers provider;

    @Parameterized.Parameters(name="Run{index}: input={0},  expected={1}, provider={2}")
    public static Iterable<Object> data() {
        return Arrays.asList(new Object[][] {
                {CabinClass.ECONOMY, "M",Providers.KIWI},
                {CabinClass.BUSINESSCLASS, "C", Providers.KIWI},
                {CabinClass.PREMIUMECONOMY, "W", Providers.KIWI},
                {CabinClass.FIRSTCLASS, "F", Providers.KIWI},
                {CabinClass.ECONOMY, "economy", Providers.RAPID},
                {CabinClass.BUSINESSCLASS, "business", Providers.RAPID},
                {CabinClass.PREMIUMECONOMY, "premiumeconomy", Providers.RAPID},
                {CabinClass.FIRSTCLASS, "first", Providers.RAPID},
        });
    }

    @Test
    public void convertCabinClassIntoStringSuccessful() throws IllegalCabinClassException {
        //When
        String actual = CabinClassConverter.convertCabinClassIntoString(input, provider);

        //Then
        assertEquals(expected, actual);
    }
}
