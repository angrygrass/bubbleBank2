package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RegisterDto;
import nl.hva.miw.c27.team1.cryptobanking.service.PepperService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.HashHelper;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.SaltMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value=("/users"))
public class UserApiController extends BaseApiController {
    private final PepperService pepperService;
    private final Logger logger = LogManager.getLogger(UserApiController.class);


    @Autowired
    public UserApiController(UserService userService, PepperService pepperService) {
        super(userService);
        this.pepperService = pepperService;
        logger.info("New UserApiController");
    }

    @PostMapping("register")
    public ResponseEntity<String> registerCustomerHandler(@RequestBody RegisterDto registerDto) {
        Customer customer = new Customer(registerDto);
        try {
            //todo refactor
            Profile profile = customer.getProfile();
            profile.setPassWordAsEntered(registerDto.getPassWord());
            profile.setSalt(new SaltMaker(SaltMaker.DEFAULT_HASH_LENGTH).generateSalt());
                profile.setHash(HashHelper.hash(profile.getPassWordAsEntered(),
                   profile.getSalt() , pepperService.getPepper()));
            userService.register(customer);

            return new ResponseEntity<>("Customer created",
                    HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {

            return new ResponseEntity<>("user already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
