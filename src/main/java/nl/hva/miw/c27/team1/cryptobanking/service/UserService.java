package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.*;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
       if (!AgeValidator.checkAge(customer)) {
            throw new RegistrationFailedExceptionAge();
       }
        if (!BSNValidator.checkBsn(customer)) {
            throw new RegistrationFailedExceptionBsn(); // unhandled exceptions, should be handled in apicontroller to return a 400
        }
        if(!ZipCodeValidator.checkZipCode(customer)) {
            throw new RegistrationFailedExceptionZipCode();
        }
        if (!IbanValidator.checkIban(customer)) {
            throw new RegistrationFailedExceptionIban();
        }
        if (!EmailValidator.checkEmail(customer)) {
            throw new RegistrationFailedExceptionEmail();
        }
        if (!PasswordValidator.checkPassWord(customer)) {
            throw new RegistrationFailedExceptionPassWord();
        }
        //This method could not be tested yet?????????????
        if (!NameValidator.checkUserName(customer)) {
            throw new RegistrationFailedExceptionUsername();

        }
        rootRepository.saveCustomer(customer);
        return customer;
            }


    //How to test following method?
    public Profile validateLogin(String userName, String passWord) {
        Optional<Profile> optionalProfile = rootRepository.getProfileByUsername(userName);
        Profile profile = optionalProfile.orElse(null);
        if (profile != null && profile.getPassWord().equals(passWord)) {
            return profile;
        } else {
            return null;
        }
    }

}
