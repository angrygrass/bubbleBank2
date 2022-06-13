package nl.hva.miw.c27.team1.cryptobanking.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utility {

    public static double roundDecimal(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
