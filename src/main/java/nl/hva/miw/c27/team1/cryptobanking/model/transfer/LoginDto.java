package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginDto {

    private String userName;
    private String password;

    //private String role;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(LoginDto.class);

//    public LoginDto() {
//        super();
//        logger.info("New loginDto 0-args");
//    };

    @Autowired
    public LoginDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
        logger.info("New loginDto all args");
    }

//    public LoginDto(User user, Profile profile) {
//        super();
//        this.userName = user.getProfile().getUserName();
//        this.passWord = user.getProfile().getPassWord();
//        //this.role = user.getRole();
//        logger.info("New loginDto all-args");
//    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }


}
