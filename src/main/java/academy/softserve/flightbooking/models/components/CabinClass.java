package academy.softserve.flightbooking.models.components;

public enum CabinClass {

    ECONOMY("M"),
    PREMIUMECONOMY("W"),
    BUSINESSCLASS("C"),
    FIRSTCLASS("F");

    private String description;

    CabinClass(String s) {
        fillDescription();
    }

    private void fillDescription(){
        CabinClass.ECONOMY.description = "Econom class";
    }

    public String getDescription(){
        return description;
    }
}
