package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RegisterDto;
import nl.hva.miw.c27.team1.cryptobanking.service.PepperService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.HashHelper;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.SaltMaker;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.UserExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


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
    public ResponseEntity<Customer> registerCustomerHandler(@RequestBody RegisterDto registerDto) throws ParseException {

        Customer customer = new Customer(registerDto);
        customer.setProfile(new Profile(registerDto.getUserName(), null, null, customer));
        Profile profile = customer.getProfile();
        customer.setBankAccount(new BankAccount(registerDto.getIban(), 5000, customer));
        profile.setPassWordAsEntered(registerDto.getPassword());
        profile.setSalt(new SaltMaker(SaltMaker.DEFAULT_HASH_LENGTH).generateSalt());
        profile.setHash(HashHelper.hash(profile.getPassWordAsEntered(), profile.getSalt(), pepperService.getPepper()));
        userService.register(customer);
        customer.getBankAccount().setCustomer(null);
        customer.getProfile().setUser(null);

        return ResponseEntity.ok(customer);

    }


}
