package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.service.PortfolioService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.NoSuchUserException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.UserExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public abstract class BaseApiController {

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(BaseApiController.class);

    protected UserService userService;
    protected PortfolioService portfolioService;

    public BaseApiController(UserService userService) {
        super();
        this.userService = userService;
        logger.info("New BaseApiController");
    }

    public BaseApiController(PortfolioService portfolioService) {
        super();
        this.portfolioService = portfolioService;
        logger.info("New BaseApiController");
    }

    public BaseApiController() {
    }

    @ExceptionHandler(NoSuchUserException.class)
    public String handleNoSuchUserException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such member");
    }

    @ExceptionHandler(UserExistsException.class)
    public  String handleUserExistsException() {
        return "User already exits";
    }

}
