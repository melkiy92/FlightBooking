package academy.softserve.flightbooking.dto;

import academy.softserve.flightbooking.models.components.CabinClass;
import academy.softserve.flightbooking.models.components.TicketType;

import java.time.format.DateTimeFormatter;

public class KiwiSearchCriterionDTO {
    private String currency;
    private String max_stopovers;
    private String selected_cabins;
    private String adults;
    private String children;
    private String fly_from;
    private String fly_to;
    private String date_from;
    private String return_from;

    private final String partner = "picky";
    private final String v = "3";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public KiwiSearchCriterionDTO() {
    }

    public KiwiSearchCriterionDTO(SearchCriterionDTO searchCriterionDTO) {
        currency = searchCriterionDTO.getCurrencyCode();
        max_stopovers = getMaxStopoversByTicketType(searchCriterionDTO.getTicketType());
        selected_cabins = convertCabbinClass(searchCriterionDTO.getCabinClass());
        adults = "" + searchCriterionDTO.getAdults();
        children = "" + searchCriterionDTO.getChildren();
        fly_from = searchCriterionDTO.getFromLocation();
        fly_to = searchCriterionDTO.getToLocation();
        date_from = dateTimeFormatter.format(searchCriterionDTO.getDepartDate());
        return_from = dateTimeFormatter.format(searchCriterionDTO.getReturnDate());
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMax_stopovers() {
        return max_stopovers;
    }

    public void setMax_stopovers(String max_stopovers) {
        this.max_stopovers = max_stopovers;
    }

    public String getSelected_cabins() {
        return selected_cabins;
    }

    public void setSelected_cabins(String selected_cabins) {
        this.selected_cabins = selected_cabins;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getFly_from() {
        return fly_from;
    }

    public void setFly_from(String fly_from) {
        this.fly_from = fly_from;
    }

    public String getFly_to() {
        return fly_to;
    }

    public void setFly_to(String fly_to) {
        this.fly_to = fly_to;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getReturn_from() {
        return return_from;
    }

    public void setReturn_from(String return_from) {
        this.return_from = return_from;
    }

    public String getPartner() {
        return partner;
    }

    public String getV() {
        return v;
    }

    private String getMaxStopoversByTicketType(TicketType ticketType) {
        if (ticketType.equals(TicketType.ONEWAY)) {
            return "0";
        } else {
            return "\"" + Integer.MAX_VALUE + "\"";
        }
    }

    private String convertCabbinClass(CabinClass cabinClass) {
        if (cabinClass.equals(CabinClass.FIRSTCLASS)) {
            return "F";
        } else if (cabinClass.equals(CabinClass.PREMIUMECONOMY)) {
            return "W";
        } else if (cabinClass.equals(CabinClass.BUSINESSCLASS)) {
            return "C";
        } else {
            return "M";
        }
    }

}
