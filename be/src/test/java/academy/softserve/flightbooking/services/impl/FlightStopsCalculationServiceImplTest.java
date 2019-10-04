package academy.softserve.flightbooking.services.impl;

import academy.softserve.flightbooking.dto.FlightDTO;
import academy.softserve.flightbooking.dto.StopDTO;
import academy.softserve.flightbooking.models.components.CabinClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FlightStopsCalculationServiceImplTest {

    @Test
    public void calculateStopsBetweenFlightsTest() {
        //GIVEN
        FlightStopsCalculationServiceImpl flightStopsCalculationService = new FlightStopsCalculationServiceImpl();

        List<FlightDTO> flightDTOList = new ArrayList<>();
        FlightDTO flightDTOFirst = new FlightDTO(
                1L,
                "3",
                "MAU",
                CabinClass.ECONOMY,
                500L,
                10L,
                "DNK",
                "Dnipro",
                100L,
                "KBP",
                "Kyiv");

        FlightDTO flightDTOSecond = new FlightDTO(
                2L,
                "5",
                "MAU",
                CabinClass.ECONOMY,
                1000L,
                200L,
                "KBP",
                "Kyiv",
                300L,
                "ZRH",
                "Zurich");

        flightDTOList.add(flightDTOFirst);
        flightDTOList.add(flightDTOSecond);

        StopDTO stopDTO = new StopDTO(100L, "KBP");
        List<StopDTO> expectedStopList = new ArrayList<>();
        expectedStopList.add(stopDTO);

        //WHEN
        List<StopDTO> actualStopList = flightStopsCalculationService.calculateStopsBetweenFlights(flightDTOList);

        //THEN
        assertEquals(expectedStopList, actualStopList);
    }

    @Test
    public void calculateStopsBetweenFlightsWithZeroSizeTest() {

        //GIVEN
        FlightStopsCalculationServiceImpl flightStopsCalculationService = new FlightStopsCalculationServiceImpl();

        List<FlightDTO> flightDTOList = new ArrayList<>();
        FlightDTO flightDTOFirst = new FlightDTO(
                1L,
                "3",
                "MAU",
                CabinClass.ECONOMY,
                500L,
                10L,
                "DNK",
                "Dnipro",
                100L,
                "KBP",
                "Kyiv");

        flightDTOList.add(flightDTOFirst);

        List<StopDTO> expectedStopList = new ArrayList<>();

        //WHEN
        List<StopDTO> actualStopList = flightStopsCalculationService.calculateStopsBetweenFlights(flightDTOList);

        //THEN
        assertEquals(expectedStopList, actualStopList);
    }

}