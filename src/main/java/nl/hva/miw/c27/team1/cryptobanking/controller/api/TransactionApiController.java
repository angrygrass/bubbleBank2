package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.TransactionDto;
import nl.hva.miw.c27.team1.cryptobanking.service.TransactionService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidTransactionRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.jfunc.json.impl.JSONObject;


@RestController
@RequestMapping(value=("/transaction"))

public class TransactionApiController extends BaseApiController {
    @JsonIgnore
    private final Logger logger = LogManager.getLogger(TransactionApiController.class);
    private TransactionService transactionService;


    public TransactionApiController(TransactionService transactionService) {

        this.transactionService = transactionService;

        logger.info("New TransactionApiController");
    }

    @GetMapping("/transaction")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transaction.html");

        return modelAndView;
    }


    @ResponseBody
    @PostMapping("/buy")
    public String buyAsset(@RequestBody TransactionDto transactionDto)
    {
        if (transactionDto.getBuyerId() == transactionDto.getSellerId()) {
            throw new InvalidTransactionRequestException();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transaction.html");

        return transactionService.doTransaction(transactionDto.getBuyerId(), transactionDto.getSellerId(),
                transactionDto.getAssetCode(), transactionDto.getQuantity());


    }



}
