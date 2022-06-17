package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeValidator {

    public static boolean checkAge(User user) {
        boolean over18 = false;
        Date birthDayDateFormat = user.getBirthDate();
        LocalDate birthDayLocalDateFormat = convertToLocalDateViaInstant(birthDayDateFormat);
        Period p = Period.between(birthDayLocalDateFormat, LocalDate.now());
        if (p.getYears() >=18) {
            over18 = true;
        }
        return over18;
    }

    //Helper method required to convert Date to LocalDate for calculation of checkAge()
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


}
