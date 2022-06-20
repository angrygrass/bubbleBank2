package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.TriggerTransactionDto;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import nl.hva.miw.c27.team1.cryptobanking.service.TriggerTransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value=("/stoporder"))
public class TriggerTransactionApiController {

    private final Logger logger = LogManager.getLogger(UserApiController.class);

    private final TriggerTransactionService triggerTransactionService;
    private final AuthorisationService authorisationService;

    @Autowired
    public TriggerTransactionApiController(TriggerTransactionService triggerTransactionService, AuthorisationService authorisationService) {
        this.triggerTransactionService = triggerTransactionService;
        this.authorisationService = authorisationService;
        logger.info("New " + TriggerTransactionApiController.class.getName());
    }

    @PostMapping
    public ResponseEntity<TriggerTransaction> registerTriggerTransaction(@RequestBody TriggerTransactionDto triggerTransactionDto, @RequestHeader(value="authorization") String authorization) {
        ResponseEntity<TriggerTransaction> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (authorisationService.checkCustomerAuthorisation(authorization)) {
            TriggerTransaction triggerTransaction = new TriggerTransaction(triggerTransactionDto);
            //check if customer has enough funds (either in crypto currency or bank) at service level
            triggerTransactionService.save(triggerTransaction);
            return ResponseEntity.ok(triggerTransaction);
        }
        return result;
    }

}
