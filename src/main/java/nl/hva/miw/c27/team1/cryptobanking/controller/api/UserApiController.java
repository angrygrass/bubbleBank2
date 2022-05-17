package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

// should this Controller also handle login?
@RestController
@RequestMapping(value=("/users"))
public class UserApiController {

    private final Logger logger = LogManager.getLogger(UserApiController.class);
    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
        logger.info("New UserApiController");
    }

    //@Get
    //getAllCustomers
    //getCustomerById
    //getCustomerByBsn
    //getCustomerByIban

    @GetMapping
    @ResponseBody
    public List getAllUsers() {
        return userService.getAllUsers();
    }

    // @Post
    //registerCustomer
    //loginHandler (or in seperate AccountController?)
    //validate ??

    public Logger getLogger() {
        return logger;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
