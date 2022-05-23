package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profile {

    private String userName;
    private String passWord;
    private User user;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(Profile.class);

    public Profile(String userName, String passWord, User user) {
        this.userName = userName;
        this.passWord = passWord;
        this.user = user;
    }


    public Profile(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }


    public Profile() {
        super();
        logger.info("New empty Profile");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Logger getLogger() {
        return logger;
    }
}
