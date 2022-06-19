package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import java.util.Calendar;
import java.util.Date;

public class DateValidator {
    public static boolean checkDate(String date) {


        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {

            String[] numbers = date.split("-");
            if (Integer.parseInt(numbers[0]) < 1900 || Integer.parseInt(numbers[0]) > 2022 || Integer.parseInt(numbers[1])
        < 1 || Integer.parseInt(numbers[1]) > 12 || Integer.parseInt(numbers[1]) < 1 || Integer.parseInt(numbers[2]) < 1 ||
            Integer.parseInt(numbers[2]) > 31) {
                return false;

            }
            if (Integer.parseInt(numbers[1]) == 2 && Integer.parseInt(numbers[2]) == 29) {
                if (Integer.parseInt(numbers[2]) == 29 && Integer.parseInt(numbers[0]) % 4 == 0) {
                    return true;
                }
                return false;


            }
            if (Integer.parseInt(numbers[1]) == 2 && Integer.parseInt(numbers[2]) > 29) {
            return false;
            }

            if (Integer.parseInt(numbers[1]) == 4 || Integer.parseInt(numbers[1]) == 6 || Integer.parseInt(numbers[1]) == 9 ||
                    Integer.parseInt(numbers[1]) == 11) {
                if (Integer.parseInt(numbers[2]) < 31) {
                    return true;
                }
                return false;

            }
            return true;
        }
        return false;
    }



}
