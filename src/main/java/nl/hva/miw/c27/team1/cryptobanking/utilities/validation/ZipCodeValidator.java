package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.User;

public class ZipCodeValidator {

    //Argument was (Customer customer)
    public static boolean checkZipCode(User user) {
        String zipcode = user.getZipCode();
        try {
            return zipcode.matches("[1-9]{1}[0-9]{3}[a-zA-Z]{2}");
        } catch (Exception e) {
            return false;
        }
    }
}
