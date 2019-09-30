package academy.softserve.flightbooking.apiconnection.converters;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class ParametersStringBuilder {
    public String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();

        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public String convertIntoJsonParameter(String key, Object value) {
        return "\"" + key + "\": " + stringifyParameter(value) + ",";
    }

    public String stringifyParameter (Object parameter) {
        String result = "";

        if(parameter.getClass().equals(String.class)) {
            result = "\"" + parameter + "\"";
        } else {
            result = "" + parameter;
        }

        return result;
    }
}
