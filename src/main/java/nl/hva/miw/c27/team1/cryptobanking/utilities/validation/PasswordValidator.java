package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;

public class PasswordValidator {
    public static boolean checkPassWord(Customer customer) {
        String passWord = customer.getProfile().getPassWord();
        if (passWord.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])" +
                "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$")) {
            return true;
        } else {
            return false;
        }
    }
}
