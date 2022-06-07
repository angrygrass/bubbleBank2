package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;


public class EmailValidator {

    public static boolean checkEmail(Customer customer) {
        String email = customer.getProfile().getUserName();
        if (email.matches("^(.+)@(\\S+)")) {
            return true;
        } else {
            return false;
        }
    }

}
