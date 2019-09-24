package academy.softserve.flightbooking.constants;

public class ApiConnectionConstants {
    public static final String KIWI_FLIGHTS_ENDPOINT = "https://api.skypicker.com/flights?";
    public static final String KIWI_MULTICITY_ENDPOINT = "https://api.skypicker.com/flights_multi?";
    public static final String KIWI_DATE_PATTERN = "dd/MM/yyyy";
    public static final Integer KIWI_RESPONSE_JSON_STRAIGHT_FLIGHT_INDEX = 0;
    public static final Integer KIWI_RESPONSE_JSON_RETURN_FLIGHT_INDEX = 1;

    public static final String RAPID_POST_ENDPOINT = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0/";
    public static final String RAPID_GET_ENDPOINT = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/";
    public static final String RAPID_X_RAPIDAPI_HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com" ;
    public static final String RAPID_X_RAPIDAPI_KEY = "5ccd3b53abmshb992db8efa8fccep18a986jsn88569fa32f67";
    public static final String RAPID_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String RAPID_DATE_PATTERN = "yyyy-MM-dd";
    public static final String RAPID_RESPONSE_JSON_DATE_TIME_PATTERN = "yyyy-MM-dd'T'hh:mm:ss";
}
