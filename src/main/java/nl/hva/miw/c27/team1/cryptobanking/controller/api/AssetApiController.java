package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.service.AssetService;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidAssetRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * End point that returns all the aoins and their current values to the customer.
 */
@CrossOrigin
@RestController
@RequestMapping(value=("/assets"))
public class AssetApiController extends BaseApiController {

    private final Logger logger = LogManager.getLogger(AssetApiController.class);
    private final AuthorisationService authorisationService;


    @Autowired
    public AssetApiController(AssetService assetService, AuthorisationService authorisationService) {
        super(assetService);
        this.authorisationService = authorisationService;
        logger.info("New AssetApiController");
    }

    @GetMapping
    public ResponseEntity<List> getAllAssetRates(@RequestHeader(value="authorization") String authorization) {
        ResponseEntity<List> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            if (authorisationService.checkCustomerAuthorisation(authorization)) {
                result = ResponseEntity.ok().body(assetService.getRates());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No assets found");
        }
        return result;
    }

    @GetMapping(value="/{name}")
    public ResponseEntity<Asset> getAssetRate(@PathVariable("name") String assetName,
                                              @RequestHeader(value="authorization") String authorization) {
        ResponseEntity<Asset> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (authorisationService.checkCustomerAuthorisation(authorization)) {
            Optional<Asset> optAsset = assetService.getRate(assetName);
            if (optAsset.isPresent()) {
                return ResponseEntity.ok().body(optAsset.get());
            } else {
                throw new InvalidAssetRequest();
            }
        }
        return result;
    }

    @GetMapping(value="/history/{name}")
    public ResponseEntity<List> getHistoricAssetRate(@PathVariable("name") String assetName,
                                                     @RequestParam("chartdays") int numberDays,
                                                     @RequestHeader(value="authorization") String authorization) {
        ResponseEntity<List> result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (authorisationService.checkCustomerAuthorisation(authorization)) {
            Optional<List> optList = assetService.getHistoricRates(assetName, numberDays);
            if (optList.isPresent()) {
                return ResponseEntity.ok().body(optList.get());
            } else {
                throw new InvalidAssetRequest();
            }
        }
        return result;
    }


}