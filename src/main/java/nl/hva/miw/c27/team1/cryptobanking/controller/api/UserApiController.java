package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.CustomerDto;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value=("/users"))
public class UserApiController extends BaseApiController {

    private final Logger logger = LogManager.getLogger(UserApiController.class);

    @Autowired
    public UserApiController(UserService userService) {
        super(userService);
        logger.info("New UserApiController");
    }

    // lijkt te werken met PostMan.
    // In een customerdto wordt geen userId meegegeven bij registratie.
    // Lijkt me dat deze bij de repository moet worden toegevoegd ?
    @PostMapping("register")
    public ResponseEntity<Customer> registerMemberHandler(@RequestBody CustomerDto customerdto) {
        Customer customer = new Customer(customerdto);
        userService.register(customer);
        return ResponseEntity.ok().body(customer);
    }


/*    @PostMapping("login")
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
    }*/

/*    @PostMapping("validate")
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
