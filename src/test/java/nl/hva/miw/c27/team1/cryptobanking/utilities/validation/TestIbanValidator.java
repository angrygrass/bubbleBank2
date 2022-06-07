package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestIbanValidator {

    //Method doesn't work, why?
    @Test
    public void check_iban_withValid_ibanNumbers() {
        Customer customer = Mockito.mock(Customer.class);
    /*
    * "NL91ABNA0417164300","NL88INGB0417164500"
    * */

        var validList = List.of("NL40ABRF3465847322");
        for (String iban: validList) {
            Mockito.when(customer.getBankAccount().getIban()).thenReturn(iban);
            assertTrue(IbanValidator.checkIban(customer));
        }

    }




}
