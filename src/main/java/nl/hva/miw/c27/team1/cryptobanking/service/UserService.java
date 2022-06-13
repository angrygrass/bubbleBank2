package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.*;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.*;


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
        // not very clean code. Perhaps do isEmpty check in frontend?
        if (customer.getFirstName().isEmpty() || customer.getSurName().isEmpty() || customer.getStreetName().isEmpty()
                || customer.getHouseNumber().isEmpty() || customer.getZipCode().isEmpty()
                || customer.getResidence().isEmpty() || customer.getCountry().isEmpty()
                || customer.getBankAccount().getIban().isEmpty()) {
            throw new RegistrationFailedExceptionFieldEmpty();
        }
        if (!AgeValidator.checkAge(customer)) {
            throw new RegistrationFailedExceptionAge();
        }
        if (!BSNValidator.checkBsn(customer)) {
            throw new RegistrationFailedExceptionBsn(); // todo unhandled exceptions, should be handled in apicontroller to return a 400
        }
        if(!ZipCodeValidator.checkZipCode(customer)) {
            throw new RegistrationFailedExceptionZipCode();
        }
        if (!IbanValidator.checkIban(customer)) {
            throw new RegistrationFailedExceptionIban();
        }
        if (!checkCountry(customer)) {
            throw new RegistrationFailedExceptionCountry();
        }
        if (!EmailValidator.checkEmail(customer)) {
            throw new RegistrationFailedExceptionEmail();
        }
        if (!PasswordValidator.checkPassWord(customer)) {
            throw new RegistrationFailedExceptionPassWord();
        }
        // todo This method could not be tested yet?????????????
        if (!checkUserName(customer)) {
            throw new RegistrationFailedExceptionUsername();
        }
        rootRepository.saveCustomer(customer);
        return customer;
    }

    private boolean checkCountry(Customer customer) {
        boolean correctCountry = false;
        for (Countries country : Countries.values()) {
            if (Objects.equals(country.toString(), customer.getCountry())) {
                correctCountry = true;
            }
        }
        return correctCountry;
    }

    private boolean checkUserName(Customer customer) {
        if (rootRepository.getProfileByUsername(customer.getProfile().getUserName()).orElse(null) != null) {
            return false;
        }
        return true;
    }

    public Optional<User> getUserByToken(Token token) {
        return rootRepository.getUserByToken(token);
    }

}
