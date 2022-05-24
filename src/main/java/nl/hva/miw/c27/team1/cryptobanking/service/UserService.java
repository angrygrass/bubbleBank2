package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * This class lies between the UserApiController class and the rootRepository.
 * Contains business logic to handle client requests.
 */
@Service
public class UserService {

    private RootRepository rootRepository;
    private final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("new UserService");
    }

    public User register(Customer customer) {
       if (!checkAge(customer)) {
            throw new RegistrationFailedExceptionAge();
       }
        if (!checkBsn(customer)) {
            throw new RegistrationFailedExceptionBsn();
        }
        if(!checkZipCode(customer)) {
            throw new RegistrationFailedExceptionZipCode();
        }
        if (!checkIban(customer)) {
            throw new RegistrationFailedExceptionIban();
        }
        if (!checkEmail(customer)) {
            throw new RegistrationFailedExceptionEmail();
        }
        if (!checkPassWord(customer)) {
            throw new RegistrationFailedExceptionPassWord();
        }
        rootRepository.save(customer);
        return customer;
    }

    /**
     * Helper method required to convert Data to LocalDate for calculation of checkAge()
     */
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public boolean checkAge(User user) {
        boolean over18 = false;
        Date birthDayDateFormat = user.getBirthDate();
        LocalDate birthDayLocalDateFormat = convertToLocalDateViaInstant(birthDayDateFormat);
        Period p = Period.between(birthDayLocalDateFormat, LocalDate.now());
        if (p.getYears() >=18) {
            over18 = true;
        }
        return over18;
    }

    /**
     * Adds individual digits of a bsn number to the ArrayList by using % 10. It then
     * multiplies each number with the 11-proef weighted number in integer[] lijst. If
     * the total % 11 == 0, then bsn is correct format.
     */
    public boolean checkBsn(User user) {
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

    /**
     * Verifies whether an email conforms to format for local and domain part. Allows for
     * upper/lowercase, values 0-9, and special characters like hyphens and dots. Consecutive
     * dots and dots at start/end are not allowed. Max 64 characters.
     */
    public boolean checkEmail(Customer customer) {
        String email = customer.getProfile().getUserName();
        if (email.matches("^(.+)@(\\S+)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifies whether password contains at least one digit, one upper and lower case character,
     * one special character and is between 8-64 characters long
     */
    public boolean checkPassWord(Customer customer) {
        String passWord = customer.getProfile().getPassWord();
        if (passWord.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])" +
                            "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIban(Customer customer) {
        try {
            IbanUtil.validate(customer.getBankAccount().getIban());
            return true;
        } catch (IbanFormatException e) {
            return false;
        }
    }

    /**
     * Verifies whether a Dutch zipcode starts with 1-9, followed by 3 numbers between
     * 0-9 and 2 letters (lower/all-caps).
     **/
    public boolean checkZipCode(Customer customer) {
        String zipcode = customer.getZipCode();
        try {
            return zipcode.matches("[1-9]{1}[0-9]{3}[a-zA-Z]{2}");
        } catch (Exception e) {
            return false;
        }
    }

    public Profile validateLogin(String userName, String passWord) {
        Optional<Profile> optionalProfile = rootRepository.getProfileOfUser(userName);
        Profile profile = optionalProfile.orElse(null);
        if (profile != null && profile.getPassWord().equals(passWord)) {
            return profile;
        } else {
            return null;
        }
    }

    // not sure if method is required/necessary here
    public void saveUser(User user) {
        rootRepository.save(user);
    }

    // getters & setters

}
