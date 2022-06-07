package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPasswordValidator {

    @Test
    public void checkPassword_with_valid_credentials_return_true() {
        Customer customer = Mockito.mock(Customer.class);
        Profile profile = Mockito.mock(Profile.class);


        /* 9Hs*m;asdd123pp;
        /* 9Hs*m@123pp;
        /* Ys*m@123p0;
        */

        var validList = List.of("9Hs*m;asdd123pp;","9Hs*m@123pp;","Ys*m@123p0");
        for (String password:validList
             ) {
            Mockito.when(customer.getProfile()).thenReturn( profile );
            Mockito.when(profile.getPassWord()).thenReturn(password);
            assertTrue(PasswordValidator.checkPassWord(customer));
        }

    }
}
