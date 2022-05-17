package nl.hva.miw.c27.team1.cryptobanking.repository.repository;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.ProfileDao;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// or below can go into 1 rootRepository which would also have methods from
// other classes (see Kwebbelweb). Perhaps useful if we need information from
// 2 DAOs in one method (e.g. when we need to link User to Profile)

// class needs db queries
@Repository
public class UserRepository {

    private List<User> userList;
    private final UserDao userDao;

    private final Logger logger = LogManager.getLogger(UserRepository.class);

    @Autowired
    public UserRepository(UserDao userDao) {
        this.userList = new ArrayList<>();
        fillUserLIst();
        this.userDao = userDao;
        logger.info("New UserRepository");
    }

    public void save(User user) {
        userList.add(user);
    }

    public void fillUserLIst() {
        userList.add(new User(1, "Client"));
        userList.add(new User(2, "Admin"));
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public Optional <User> getUserById(int id) {
        return userList.stream().filter(u -> u.getId() == id).findFirst();
    }

    public User getUserByRole(String role) {
        return userList.stream().filter(u -> u.getRole().equals(role)).findFirst().get();
    }

    public User update(User user) {
        int indexOfUserToUpdate = userList.indexOf(user);
        if (indexOfUserToUpdate >= 0) {
            userList.set(indexOfUserToUpdate, user);
            return userList.get(indexOfUserToUpdate);
        } else {
            return null;
        }
    }

    public void delete(int id) {
        Optional<User> user = getUserById(id);
        int indexOfUserToUpdate = userList.indexOf(user);
        System.out.println(indexOfUserToUpdate);
        if (indexOfUserToUpdate >= 0) {
            userList.remove(indexOfUserToUpdate);
        } else {
            System.out.println("niet gelukt");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // getters & setters
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Logger getLogger() {
        return logger;
    }

}
