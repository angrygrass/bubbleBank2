package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.service.LoginService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping
public class LoginApiController extends BaseApiController {
    private final Logger logger = LogManager.getLogger(LoginApiController.class);
    private LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
        logger.info("New LoginApiController");
    }

    @GetMapping("/login")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        //
        return modelAndView;
    }


    @ResponseBody
    @PostMapping("/login")
    public String loginUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        //the password should be sent via https, but as I understand we aren't going to do that?
        //todo jjs hier verder wat voor object geef je terug bij een post??!

        String token = loginService.login(username, password);
        //we should create a token and register the token in the database
        //todo jjs add token to database
        return token;
    }

}
