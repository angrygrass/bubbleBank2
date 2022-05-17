package nl.hva.miw.c27.team1.cryptobanking.repository.repository;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> userList;
    private final Logger logger = LogManager.getLogger(UserRepository.class);

    @Autowired
    public UserRepository() {
        this.userList = new ArrayList<>();
        fillUserLIst();
        logger.info("New UserRepository");
    }

    public void fillUserLIst() {
        userList.add(new User(2));
    }

    public List<User> getAllUsers() {
        return userList;
    }

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
