package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestZipCodeValidator {


    @Test
    public void check_zipCode_withValidData_return_True() {
        User user = Mockito.mock(User.class);

        /*
        * 8099KK
        * 7388ML
        * 8898LK
        * 6533ZZ
        * */
        var validList  = List.of("8099KK", "7388ML", "8898LK", "6533ZZ");

        for(String zipCode: validList){
            Mockito.when(user.getZipCode()).thenReturn(zipCode); // Valid zip codes
            assertTrue(ZipCodeValidator.checkZipCode(user));
        }
    }

    @Test
    public void check_zipCode_withInValidData_return_True() {
        User user = Mockito.mock(User.class);


        var validList  = List.of("8099KdK", "7388MsdL", "8898sdLK", "653  3ZZ");

        for(String zipCode: validList){
            Mockito.when(user.getZipCode()).thenReturn(zipCode); // Valid zip codes
            assertFalse(ZipCodeValidator.checkZipCode(user));
        }
    }
}
