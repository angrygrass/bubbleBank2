package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.BuyAssetDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.service.BuyAssetService;

import nl.hva.miw.c27.team1.cryptobanking.service.PortfolioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping(value=("/assets/buy"))
public class BuyAssetApiController extends BaseApiController {
    @JsonIgnore
    private final Logger logger = LogManager.getLogger(BuyAssetApiController.class);
    private BuyAssetService buyAssetService;


    public BuyAssetApiController(BuyAssetService buyAssetService) {

        this.buyAssetService = buyAssetService;

        logger.info("New BuyAssetApiController");
    }

    @GetMapping("/buyfrombank")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buyfrombank.html");
            //
        return modelAndView;
    }


    @ResponseBody
    @PostMapping("/buyfrombank")
    public String buyAsset(@RequestBody BuyAssetDto buyAssetDto)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buyfrombank.html");

        return buyAssetService.buyFromBank(buyAssetDto.getUserid(), buyAssetDto.getAssetCode(), buyAssetDto.getQuantity());


    }

}


