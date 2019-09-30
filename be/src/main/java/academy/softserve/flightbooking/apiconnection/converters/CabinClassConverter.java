package academy.softserve.flightbooking.apiconnection.converters;

import academy.softserve.flightbooking.apiconnection.connectors.Providers;
import academy.softserve.flightbooking.exceptions.IllegalCabinClassException;
import academy.softserve.flightbooking.models.components.CabinClass;

public class CabinClassConverter {
    public static String convertCabinClassIntoString(CabinClass cabinClass, Providers providerName) throws IllegalCabinClassException {
        if(providerName.equals(Providers.KIWI)) {
           return convertCabinClassIntoStringForKiwi(cabinClass);
        } else if (providerName.equals(Providers.RAPID)) {
            return convertCabinClassIntoStringForRapid(cabinClass);
        } else {
            throw new IllegalCabinClassException();
        }
    }

    public static CabinClass convertStringIntoCabinClass (String cabinClassName, Providers providerName) throws IllegalCabinClassException {
        if(providerName.equals(Providers.KIWI)) {
            return convertStringIntoCabinClassForKiwi(cabinClassName);
        } else if (providerName.equals(Providers.RAPID)) {
            return convertStringIntoCabinClassForRapid(cabinClassName);
        } else {
            throw new IllegalCabinClassException();
        }
    }

    private static String convertCabinClassIntoStringForKiwi(CabinClass cabinClass) throws IllegalCabinClassException {
        if (cabinClass.equals(CabinClass.FIRSTCLASS)) {
            return "F";
        } else if (cabinClass.equals(CabinClass.PREMIUMECONOMY)) {
            return "W";
        } else if (cabinClass.equals(CabinClass.BUSINESSCLASS)) {
            return "C";
        } else if (cabinClass.equals(CabinClass.ECONOMY)) {
            return "M";
        } else {
            throw new IllegalCabinClassException();
        }
    }

    private static String convertCabinClassIntoStringForRapid(CabinClass cabinClass) throws IllegalCabinClassException {
        if (cabinClass.equals(CabinClass.FIRSTCLASS)) {
            return "first";
        } else if (cabinClass.equals(CabinClass.PREMIUMECONOMY)) {
            return "premiumeconomy";
        } else if (cabinClass.equals(CabinClass.BUSINESSCLASS)) {
            return "business";
        } else if (cabinClass.equals(CabinClass.ECONOMY)) {
            return "economy";
        } else {
            throw new IllegalCabinClassException();
        }
    }

    private static CabinClass convertStringIntoCabinClassForKiwi(String cabinClassName) throws IllegalCabinClassException {
        if (cabinClassName.equals("F")) {
            return CabinClass.FIRSTCLASS;
        } else if (cabinClassName.equals("W")) {
            return CabinClass.PREMIUMECONOMY;
        } else if (cabinClassName.equals("C")) {
            return CabinClass.BUSINESSCLASS;
        } else if (cabinClassName.equals("M")) {
            return CabinClass.ECONOMY;
        } else {
            throw new IllegalCabinClassException();
        }
    }

    private static CabinClass convertStringIntoCabinClassForRapid(String cabinClassName) throws IllegalCabinClassException {
        if (cabinClassName.equals("first")) {
            return CabinClass.FIRSTCLASS;
        } else if (cabinClassName.equals("premiumeconomy")) {
            return CabinClass.PREMIUMECONOMY;
        } else if (cabinClassName.equals("business")) {
            return CabinClass.BUSINESSCLASS;
        } else if (cabinClassName.equals("economy")) {
            return CabinClass.ECONOMY;
        } else {
            throw new IllegalCabinClassException();
        }
    }
}
