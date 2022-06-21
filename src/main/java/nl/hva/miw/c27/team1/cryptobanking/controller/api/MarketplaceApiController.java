package nl.hva.miw.c27.team1.cryptobanking.controller.api;


import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.MarketplaceDto;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.TransactionDto;
import nl.hva.miw.c27.team1.cryptobanking.service.*;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidTransactionRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value=("/marketplace"))
public class MarketplaceApiController extends BaseApiController {

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(MarketplaceApiController.class);

    private AuthenticationService authenticationService;
    private AuthorisationService authorisationService;
    private MarketplaceService marketplaceService;
    private TransactionService transactionService;

    public MarketplaceApiController(AuthenticationService authenticationService, AuthorisationService authorisationService, MarketplaceService marketplaceService, TransactionService transactionService) {
        this.authenticationService = authenticationService;
        this.authorisationService = authorisationService;
        this.marketplaceService = marketplaceService;
        this.transactionService = transactionService;
        logger.info("new MarketplaceApiController");
    }

    @GetMapping
    public ResponseEntity<List<MarketplaceOffer>> getAllOffers(@RequestHeader(value="authorization") String authorization) {
        ResponseEntity<List<MarketplaceOffer>> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            if (authorisationService.checkCustomerAuthorisation(authorization)) {
                result = ResponseEntity.ok().body(marketplaceService.getOffers()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No offers found");
        }
        return result;
    }

    @ResponseBody

    @PostMapping("/make")
    public ResponseEntity<MarketplaceOffer> makeOffer (@RequestBody MarketplaceDto marketplaceDto,
                                          @RequestHeader(value="authorization") String authorization) throws Exception {
        ResponseEntity<MarketplaceOffer> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (authorisationService.checkCustomerAuthorisation(authorization))   {
            return ResponseEntity.ok().body(marketplaceService.makeOffer(marketplaceDto.getUserId(), marketplaceDto.getAssetCode(),
                    marketplaceDto.getQuantity(), marketplaceDto.getPrice(), marketplaceDto.isSellYesOrNo()));

        }
        return result;
    }


    @PostMapping("/accept")
    public ResponseEntity<Transaction> acceptOffer (@RequestParam int userId, int offerId,
                                                @RequestHeader(value="authorization") String authorization) throws Exception {
        ResponseEntity<Transaction> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (authorisationService.checkCustomerAuthorisation(authorization))   {
            Transaction transaction = marketplaceService.acceptOffer(userId, offerId);
            return ResponseEntity.ok().body(transaction);
        }
        return result;
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelOffer (@RequestParam int offerId,
                                                    @RequestHeader(value="authorization") String authorization) throws Exception {
        ResponseEntity<?> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (authorisationService.checkCustomerAuthorisation(authorization))   {
            marketplaceService.cancelOffer(offerId);
            return ResponseEntity.ok().body("Offer canceled");
        }
        return result;
    }




}
