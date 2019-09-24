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
public class CabinClassConverterStringIntoCabinClassTest {
    private String input;
    private CabinClass expected;
    private Providers provider;

    @Parameterized.Parameters(name="Run{index}: input={0}, expected={1}, provider={2}")
    public static Iterable<Object> data() {
        return Arrays.asList(new Object[][] {
                {"M", CabinClass.ECONOMY, Providers.KIWI},
                {"C",  CabinClass.BUSINESSCLASS, Providers.KIWI},
                {"W", CabinClass.PREMIUMECONOMY, Providers.KIWI},
                {"F", CabinClass.FIRSTCLASS,   Providers.KIWI},
                {"economy", CabinClass.ECONOMY, Providers.RAPID},
                {"business", CabinClass.BUSINESSCLASS, Providers.RAPID},
                {"premiumeconomy", CabinClass.PREMIUMECONOMY, Providers.RAPID},
                {"first", CabinClass.FIRSTCLASS, Providers.RAPID},
        });
    }

    @Test
    public void convertStringIntoCabinClassSuccessful() throws IllegalCabinClassException {
        //When
        CabinClass actual = CabinClassConverter.convertStringIntoCabinClass(input, provider);

        //Then
        assertEquals(expected, actual);
    }
}
