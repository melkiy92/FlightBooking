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
            if(correspondenceMap.containsKey(key)) {
                String correspondenceKeyValue = correspondenceMap.get(key);
                response.put(correspondenceKeyValue, entry.getValue());
            } else {
                response.put(entry.getKey(), entry.getValue());
            }
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
