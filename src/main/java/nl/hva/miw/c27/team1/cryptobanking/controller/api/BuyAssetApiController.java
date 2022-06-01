package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.BuyAssetDto;
import nl.hva.miw.c27.team1.cryptobanking.service.BuyAssetService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping(value=("/assets"))
public class BuyAssetApiController extends BaseApiController {
    @JsonIgnore
    private final Logger logger = LogManager.getLogger(BuyAssetApiController.class);
    private BuyAssetService buyAssetService;

    public BuyAssetApiController(BuyAssetService buyAssetService) {
        this.buyAssetService = buyAssetService;
        logger.info("New BuyAssetApiController");
    }

    @GetMapping("/assets")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buyasset.html");
            //
        return modelAndView;
    }


    @ResponseBody
    @PostMapping("/buyfrombank")
    public String buyAsset(@RequestBody BuyAssetDto buyAssetDto)
    {


        return buyAssetService.buyFromBank(buyAssetDto.getUserid(), buyAssetDto.getAssetCode(), buyAssetDto.getQuantity());


    }

}


