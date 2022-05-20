package nl.hva.miw.c27.team1.cryptobanking.controller.web;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmationController {

    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(ConfirmationController.class);

    @Autowired
    public ConfirmationController(UserService userService) {
        super();
        this.userService = userService;
        logger.info("New ConfirmationController.");
    }

    @PostMapping("/confirm")
    public String confirmHandler(@RequestParam(name="member") User confirmedUser) {
        userService.saveUser(confirmedUser);
        logger.info("De bevestigde gebruiker is " + confirmedUser);
        return "home";
    }


}
