package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.LoginDto;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthenticationService;
import nl.hva.miw.c27.team1.cryptobanking.service.HashService;
import nl.hva.miw.c27.team1.cryptobanking.service.PepperService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.NoSuchUserPasswordCombination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginApiController extends BaseApiController {
    private final Logger logger = LogManager.getLogger(LoginApiController.class);
    private UserService userService;
    private HashService hashService;
    private AuthenticationService authenticationService;
    private final PepperService pepperService;

    public LoginApiController(AuthenticationService authenticationService,
                              UserService userService, HashService hashService, PepperService pepperService) {
        this.userService = userService;
        this.hashService = hashService;
        this.authenticationService = authenticationService;
        this.pepperService = pepperService;
        logger.info("New LoginApiController");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> loginHandler(@RequestBody LoginDto loginDto) {
        //default value for time to keep token is a day
        final int MINUTES_IN_DAY = 1440;
        String salt = hashService.getSaltByUserName(loginDto.getUserName());
        if (!salt.equals("")) {
            String hashedPassword = hashService.hash(loginDto.getPassword(),
                    salt, pepperService.getPepper());
            Profile profile = authenticationService.validateLogin(loginDto.getUserName(), hashedPassword);
            if (profile != null) {
                //get and save a token for identification
                authenticationService.save(new Token(authenticationService.createToken(),
                        authenticationService.getTimestampNowWithAddedMinutes(MINUTES_IN_DAY), profile.getUser()));
                return ResponseEntity.ok("login successful");
            }
        }
        throw new NoSuchUserPasswordCombination();
    }

}
