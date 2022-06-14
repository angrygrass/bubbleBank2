package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authorization.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthorisationService {
    private final Logger logger = LogManager.getLogger(AuthenticationService.class);
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthorisationService(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        logger.info(AuthenticationService.class.getName());
    }

    public boolean checkAdminAuthorisation(String authorisationHeader) {
        return checkAuthorisationForRole(authorisationHeader, Role.Admin);
    }

    public boolean checkCustomerAuthorisation(String authorisationHeader) {
        return checkAuthorisationForRole(authorisationHeader, Role.Customer);
    }

    private boolean checkAuthorisationForRole(String authorisationHeader, Role role) {
        String token = authenticationService.extractTokenFromBearer(authorisationHeader);
        User user = userService.getUserByTokenID(token).orElse(null);
        if (user != null && !user.getRole().isEmpty() && user.getRole().equals(role.name()) &&
                authenticationService.validateToken(token, user)) return true; else return false;
    }


}
