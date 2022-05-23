package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;


public class Token {

    private String tokenId;
    private Timestamp validuntil;
    private User user;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(Profile.class);

    public Token(String tokenId, Timestamp validuntil, User user) {
        this.tokenId = tokenId;
        this.validuntil = validuntil;
        this.user = user;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Timestamp getValiduntil() {
        return validuntil;
    }

    public void setValiduntil(Timestamp validuntil) {
        this.validuntil = validuntil;
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
