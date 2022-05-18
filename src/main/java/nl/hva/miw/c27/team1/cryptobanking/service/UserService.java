package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.RegistrationFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;
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

    public User register(User user) {
        Optional<User> existing = rootRepository.getUserById(user.getId());
        if (existing.isPresent()) {
            throw new RegistrationFailedException();
        }
        rootRepository.save(user);
        return user;
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

    // niet zeker of dit nodig is op deze plek
    public void saveUser(User user) {
        rootRepository.save(user);
    }


    // getters & setters


}
