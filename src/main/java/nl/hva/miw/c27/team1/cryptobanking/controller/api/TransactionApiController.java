package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.TransactionDto;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import nl.hva.miw.c27.team1.cryptobanking.service.TransactionService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidTransactionRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value=("/transaction"))

public class TransactionApiController extends BaseApiController {
    @JsonIgnore
    private final Logger logger = LogManager.getLogger(TransactionApiController.class);
    private TransactionService transactionService;
    private AuthorisationService authorisationService;


    public TransactionApiController(TransactionService transactionService,  AuthorisationService authorisationService) {

        this.transactionService = transactionService;
        this.authorisationService = authorisationService;

        logger.info("New TransactionApiController");
    }

    @GetMapping("/transaction")
    public ModelAndView welcome(@RequestHeader(value="authorization") String authorization) {
        ModelAndView result = new ModelAndView();

        if (authorisationService.checkCustomerAuthorisation(authorization)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("transaction.html");
            return modelAndView;
        }
        result.setStatus(HttpStatus.FORBIDDEN);
        return result;
    }

    @ResponseBody

    @PostMapping("/buy")
    public ResponseEntity<String> buyAsset(@RequestBody TransactionDto transactionDto,
                                           @RequestHeader(value="authorization") String authorization) {
        ResponseEntity<String> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (authorisationService.checkCustomerAuthorisation(authorization))   {
            if (transactionDto.getBuyerId() == transactionDto.getSellerId()) {
                throw new InvalidTransactionRequestException();
            }


            String transactionMsg = transactionDto.getBuyerId() + " has bought " + transactionDto.getQuantity() +
                    " " + transactionDto.getAssetCode() + " from " + transactionDto.getSellerId();
            return ResponseEntity.ok().body(transactionMsg);

        }

            return result;

    }

}
