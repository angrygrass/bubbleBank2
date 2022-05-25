package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RegisterDto;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value=("/users"))
public class UserApiController extends BaseApiController {

    private final Logger logger = LogManager.getLogger(UserApiController.class);

    @Autowired
    public UserApiController(UserService userService) {
        super(userService);
        logger.info("New UserApiController");
    }

  /* client geeft een dto object mee in json-formaat. Dit wordt opgeslagen als een Customer object dmv
     een constructor.
  */
  /*  @PostMapping("register")
    public ResponseEntity<Customer> registerCustomerHandler(@RequestBody RegisterDto registerDto) {
        Customer customer = new Customer(registerDto);
        userService.register(customer);
        //return ResponseEntity.ok().body(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }*/

    @PostMapping("register")
    public ResponseEntity<String> registerCustomerHandler(@RequestBody RegisterDto registerDto) {
        Customer customer = new Customer(registerDto);
        try {

            userService.register(customer);

            return new ResponseEntity<>("Customer created",
                    HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {

            return new ResponseEntity<>("user already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    //gets html from a default 'resources/public' or 'resources/static' folder
    //todo jjs
//    @RequestMapping(path="/login")
//    public String getWelcomePage(){
//        return "login.html";
//    }

    //test jjs
    @RequestMapping("/welcome")
    public String welcomepage() {
        return "Welcome to Yawin Tutor";
    }

    @RequestMapping("/login2")
    public ModelAndView welcome2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }


    @GetMapping("/greeting")
    public String greeting(Model model) {
        //model.addAttribute("name", name);
        return "greeting";
    }

 /*   @PostMapping("login")
    public ResponseEntity<CustomerDto> loginHandler(@RequestBody LoginDto loginDto) {
        // user en niet customer omdat een admin ook moet inloggen (?)
        User user = userService.validateLogin(
                loginDto.getUsername(), loginDto.getPassword());
        if (user != null) {
            TokenMemberPair tokenMemberPair = authorizationService.authorize(user);
            return ResponseEntity.ok()
                    .header("Authorization", tokenMemberPair.getKey().toString())
                    .body(new UserDto(user));
        }
        throw new LoginException();
    }

    @PostMapping("validate")
    public ResponseEntity<String> validationHandler(@RequestHeader String authorization) {
        try {
            UUID uuid = UUID.fromString(authorization);
            Optional<User> user = authorizationService.validate(uuid);
            if (user.isPresent()) {
                return ResponseEntity.ok().body(user.get().getFullname());
            } else {
                throw new LoginException();
            }
        } catch (IllegalArgumentException e) {
            throw new LoginException();
        }
    }*/

}
