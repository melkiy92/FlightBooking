package academy.softserve.flightbooking.apiconnection;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SearchParamsIntoRapidApiConverter {
    private Map<String, String> correspondenceMap;

    public SearchParamsIntoRapidApiConverter() {
        correspondenceMap = initializeCorrespondenceMap();
    }

    public Map<String, String> convertIntoRequest (Map<String, String> searchCriteria) {
        Map<String, String> response = new HashMap<>();

        for (Map.Entry<String, String> entry : searchCriteria.entrySet()) {
            String key = entry.getKey();

            // converts date format dd/mm/yyyy into yyyy-mm-dd
            if(entry.getKey().equals("date_from")) {
                System.out.println(entry.getValue());
                String date = entry.getValue();
                String day = date.substring(0,2);
                String month = date.substring(3,5);
                String year = date.substring(6);
                StringBuilder newDate = new StringBuilder();
                newDate.append(year).append("-").append(month).append("-").append(day);

                System.out.println(correspondenceMap.get(entry.getKey()));
                response.put(correspondenceMap.get(entry.getKey()), newDate.toString());
//                entry.setValue(newDate.toString());
            }

            if(entry.getKey().equals("fly_from") | entry.getKey().equals("fly_to")) {
                System.out.println(entry.getValue());
                String airportSignature = entry.getValue();
                String newAirportSignature = airportSignature + "-sky";
                response.put(correspondenceMap.get(entry.getKey()), newAirportSignature);
//                entry.setValue(newAirportSignature);
            }

            if(correspondenceMap.containsKey(key)) {
                String correspondenceKeyValue = correspondenceMap.get(key);
                response.put(correspondenceKeyValue, entry.getValue());
            } else {
                response.put(entry.getKey(), entry.getValue());
            }

        }

        for(Map.Entry<String, String> entry : response.entrySet()) {
            System.out.println("\"" + entry.getKey() + "\" : \"" + entry.getValue() + "\"");
        }
        return response;
    }

    private Map<String, String> initializeCorrespondenceMap() {
        Map<String, String> map = new HashMap<>();

        map.put("fly_from", "originPlace");
        map.put("fly_to", "destinationPlace");
        map.put("date_from", "outboundDate");
        map.put("country", "country");
        map.put("adults", "adults");
        map.put("currency", "currency");
        map.put("locale", "locale");

        return map;
    }
}
