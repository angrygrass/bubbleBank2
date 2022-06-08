package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.service.PortfolioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value=("/portfolio/assets"))
public class PortfolioApiController extends BaseApiController {

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(PortfolioApiController.class);

    @Autowired
    public PortfolioApiController(PortfolioService portfolioService) {
        super(portfolioService);
        logger.info("new empty PortfolioApiController");
    }

    // before you can access portofolio, first you need to call upon authorization service to check whether visitor
    // is allowed.
    @GetMapping
    public ResponseEntity<Portfolio> showPortfolioHandler(@RequestHeader(value="authorization") String authorization) {
        System.out.println(authorization);
        // some method like validateAuthorization(authorization) from registrationService class? ?
        // if return is customer, then call portfolioService.getPortfolio(Customer customer) with if statement ?

        try {
            // placeholder
            Customer testCustomer = new Customer(3,"Customer", "Van","Hallo",
                    111222333 , new SimpleDateFormat("yyyy-MM-dd").parse("1937-12-16"),
                    "Acoma St","2222", "2316LS","Proston", "Nederland",
                    new Profile("test@gmail.com","Paswoord12?"),
                    new BankAccount("NL02ABNA0123456789",5000),new Portfolio(), new ArrayList<>());
            // makes sense?

            Portfolio portfolio = portfolioService.getPortfolio(testCustomer);
            return ResponseEntity.ok().body(portfolio);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<>(HttpStatus.OK);
    }



}
