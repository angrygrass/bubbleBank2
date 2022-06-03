package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.service.AssetService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidAssetRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * End point that returns all the aoins and their current values to the customer.
 */
@RestController
@RequestMapping(value=("/assets_all"))
public class AssetApiController extends BaseApiController {

    private final Logger logger = LogManager.getLogger(AssetApiController.class);

    @Autowired
    public AssetApiController(AssetService assetService) {
        super(assetService);
        logger.info("New AssetApiController");
    }

    @GetMapping
    public List<Asset> getAllAssetRates(@RequestHeader(value="authorization") String authorization) {
        try {
            return assetService.getRates();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No assets found");
        }
    }

    @GetMapping(value="/{name}")
    public ResponseEntity<Asset> getAssetRate(@PathVariable("name") String assetName,
                                              @RequestHeader(value="authorization") String authorization) {
        Optional<Asset> optAsset = assetService.getRate(assetName);
        if (optAsset.isPresent()) {
            return ResponseEntity.ok().body(optAsset.get());
        } else {
            throw new InvalidAssetRequest();
        }
    }


}