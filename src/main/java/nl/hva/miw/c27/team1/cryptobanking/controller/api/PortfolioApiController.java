package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthenticationService;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import nl.hva.miw.c27.team1.cryptobanking.service.PortfolioService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value=("/portfolio"))
public class PortfolioApiController extends BaseApiController {

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(PortfolioApiController.class);

    private AuthenticationService authenticationService;
    private AuthorisationService authorisationService;
    private UserService userService;

    @Autowired
    public PortfolioApiController(PortfolioService portfolioService, AuthenticationService authenticationService, AuthorisationService authorisationService, UserService userService) {
        super(portfolioService);
        this.authenticationService = authenticationService;
        this.authorisationService = authorisationService;
        this.userService = userService;
        logger.info("new empty PortfolioApiController");
    }

    @GetMapping
    public ResponseEntity<Portfolio> showPortfolioHandler(@RequestHeader(value="authorization") String authorization) {
        System.out.println(authorization);
        ResponseEntity<Portfolio> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        //get user by token and authorise that token
        String token = authenticationService.extractTokenFromBearer(authorization);
        User user = userService.getUserByTokenID(token).orElse(null);
        if (user != null && authorisationService.checkCustomerAuthorisation(authorization)) {
            try {
                Customer customer = (Customer) user;
                    Portfolio portfolio = portfolioService.getPortfolio(customer);
                return ResponseEntity.ok().body(portfolio);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return result;
    }



}
