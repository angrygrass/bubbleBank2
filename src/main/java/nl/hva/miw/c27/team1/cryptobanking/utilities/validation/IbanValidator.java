package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;

public class IbanValidator {

    public static boolean checkIban(Customer customer) {
        try {
            IbanUtil.validate(customer.getBankAccount().getIban());
            return true;
        } catch (IbanFormatException e) {
            return false;
        }
    }

}
