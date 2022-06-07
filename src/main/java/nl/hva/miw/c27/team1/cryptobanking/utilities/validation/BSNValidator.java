package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.User;

import java.util.ArrayList;

public class BSNValidator {
    /**
     * Adds individual digits of a bsn number to the ArrayList by using % 10. It then
     * multiplies each number with the 11-proef weighted number in integer[] lijst. If
     * the total % 11 == 0, then bsn is correct format.
     */
    public static boolean checkBsn(User user) {
        ArrayList<Integer> individualBsnDigits = new ArrayList<>();
        // numbers for '11-proef' BSN number
        Integer[] lijst = new Integer[] {-1,2,3,4,5,6,7,8,9};
        boolean correcteFormat = false;
        int sum11Proef = 0;
        int bsnOfUser = user.getBsnNumber();
        while (bsnOfUser > 0) {
            individualBsnDigits.add(bsnOfUser % 10);
            bsnOfUser = bsnOfUser / 10;
        }
        for (int i = 0; i < individualBsnDigits.size(); i++) {
            sum11Proef = sum11Proef + (individualBsnDigits.get(i) * lijst[i]);
        }
        if (individualBsnDigits.size() == 9 && sum11Proef % 11 == 0) {
            correcteFormat = true;
        }
        return correcteFormat;
    }
}
