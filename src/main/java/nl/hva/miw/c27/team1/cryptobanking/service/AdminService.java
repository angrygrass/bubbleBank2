package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class lies between the AdminApiController class and the rootRepository.
 * Contains business logic. In next sprints this will be furhter developed.
 */
@Service
public class AdminService {

    private RootRepository rootRepository;
    private final Logger logger = LogManager.getLogger(AdminService.class);

    @Autowired
    public AdminService(RootRepository userRepository) {
        this.rootRepository = userRepository;
        logger.info("New UserService");
    }

    public void saveUser(User user) {
        rootRepository.save(user);
    }

    public List<User> getAllUsers() {
        return rootRepository.getAllUsers();
    }

    public Optional<User> getUserById(int id) {
        return rootRepository.getUserById(id);
    }

    public User getUserByRole(String role) {
        return rootRepository.getUserByRole(role);
    }

    public User updateUser(User user) {
        return rootRepository.update(user);
    }

    public void deleteUser(int id) {
        rootRepository.delete(id);
    }

    // getters & setters
    public RootRepository getRootRepository() {
        return rootRepository;
    }

    public void setRootRepository(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
    }

    public Logger getLogger() {
        return logger;
    }
}
