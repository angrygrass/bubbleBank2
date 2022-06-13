package nl.hva.miw.c27.team1.cryptobanking.model;

import nl.hva.miw.c27.team1.cryptobanking.service.HashService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profile {

    private String userName;
    private String hash;
    private String salt;
    private User user;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonIgnore
    private HashService hashService;

    @JsonIgnore
    private String passWordAsEntered;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(Profile.class);

    public String getPassWordAsEntered() {
        return passWordAsEntered;
    }

    public void setPassWordAsEntered(String passWordAsEntered) {
        this.passWordAsEntered = passWordAsEntered;
    }

    //todo jjs
    public Profile(String userName, String hash, String salt, User user) {
        this.userName = userName;
        this.hash = hash;
        //this.passWordAsEntered = passWord;
        this.salt = salt;
        this.user = user;
    }

    public Profile(String userName, String hash, String salt, String passWord) {
        this.userName = userName;
        this.hash = hash;
        this.salt = salt;
        this.passWordAsEntered = passWord;
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
