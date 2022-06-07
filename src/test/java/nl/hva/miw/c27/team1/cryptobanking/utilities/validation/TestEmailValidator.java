package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEmailValidator {

    @Test
    public void check_email_with_valid_emailAddresses_return_true() {
        Customer customer = Mockito.mock(Customer.class);
        Profile profile = Mockito.mock(Profile.class);

        /*
        * abc@gmail.com
        * try123@hotmail.com
        * makeit@hva.nl
        * */

        var validList = List.of("abc@gmail.com","try123@hotmail.com","makeit@hva.nl");
        for (String email:validList) {
            Mockito.when(customer.getProfile()).thenReturn(profile);
            Mockito.when(profile.getUserName()).thenReturn(email);
            assertTrue(EmailValidator.checkEmail(customer));
        }
    }

    @Test
    public void check_email_with_invalid_emailAddresses_return_true() {
        Customer customer = Mockito.mock(Customer.class);
        Profile profile = Mockito.mock(Profile.class);

        /*
         * abcgmailcom
         * try123hotmail.com
         * makeithva.nl
         * */

        var validList = List.of("abcgmailcom","try123hotmailcom","makeithvanl");
        for (String email:validList) {
            Mockito.when(customer.getProfile()).thenReturn(profile);
            Mockito.when(profile.getUserName()).thenReturn(email);
            assertFalse(EmailValidator.checkEmail(customer));
        }
    }


}
