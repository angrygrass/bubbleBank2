package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.RegistrationFailedExceptionAge;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.RegistrationFailedExceptionBsn;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.RegistrationFailedExceptionExistingUser;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.RegistrationFailedExceptionIban;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;

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
        Optional<User> existing = rootRepository.getUserById(customer.getId());
        if (existing.isPresent()) {
            throw new RegistrationFailedExceptionExistingUser();
        }
        // checken if below does not result in NullPointer and that throwing of
        // exception does not lead to .save(user)
        if (!checkAge(customer)) {
            throw new RegistrationFailedExceptionAge();
        }
        if (!checkBsn(customer)) {
            throw new RegistrationFailedExceptionBsn();
        }
        if (!checkIban(customer)) {
            throw new RegistrationFailedExceptionIban();
        }
        rootRepository.save(customer);
        return customer;
    }

    // business logic voor registratie. Double check of hier juiste plaats is
    public boolean checkAge(User user) {
        boolean over18 = false;
        LocalDate dateToday = LocalDate.now();
        LocalDate birthDay = user.getBirthDate();
        Period p = Period.between(birthDay, dateToday);
        if (p.getYears() >= 18) {
            over18 = true;
        }
        return over18;
    }

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

    // needs test if method is correct
    public boolean checkIban(Customer customer) {
        boolean correcteIban = false;
        try {
            IbanUtil.validate(customer.getBankAccount().getIban());
            return true;
        } catch (IbanFormatException e) {
            e.getMessage();
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
