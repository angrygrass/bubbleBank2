package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAgeValidator {

    @Test
    public void checkAge_has_valid_age_should_return_true() throws ParseException {
        User user = Mockito.mock(User.class);

        var validList = List.of(
                (new SimpleDateFormat("yyyy-MM-dd").parse("1937-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("1970-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("1990-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("2000-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("2003-12-16")));
        for(Date birthDate: validList){
            Mockito.when(user.getBirthDate()).thenReturn(birthDate); //  valid date of births'
            assertTrue(AgeValidator.checkAge(user));
        }

    }

    @Test
    public void checkAge_has_invalid_age_should_return_true() throws ParseException {
        User user = Mockito.mock(User.class);

        var validList = List.of(
                (new SimpleDateFormat("yyyy-MM-dd").parse("2007-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("2010-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-16")),
                (new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-16")));
        for(Date birthDate: validList){
            Mockito.when(user.getBirthDate()).thenReturn(birthDate); // a valid date of birth
            assertFalse(AgeValidator.checkAge(user));
        }

    }


}
