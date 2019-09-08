package academy.softserve.flightbooking.apiconnection;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SearchParamsIntoKiwiApiConverter {

    private Map<String, String> correspondenceMap;

    public SearchParamsIntoKiwiApiConverter() {
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
        response.put("partner", "picky");
        return response;
    }

    private Map<String, String> initializeCorrespondenceMap() {
        Map<String, String> map = new HashMap<>();
        map.put("fly_from", "fly_from");
        map.put("fly_to", "fly_to");
        map.put("date_from", "date_from");
        map.put("date_to", "date_to");
        map.put("adults", "adults");
        return map;
    }
}
