package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
A Dto class limits the amount of sensitive data send. Only provided what is needed.
In Java applications - entity classes are used to represent tables in a relational database. Without DTOs,
we'd have to expose the entire entities to a remote interface. This causes a strong coupling between
an API and a persistence model.
*/
public class LoginDto {

    private String userName;
    private String passWord;

    private final Logger logger = LoggerFactory.getLogger(LoginDto.class);

    public LoginDto() {
        super();
        logger.info("New LoginDto.");
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
}
