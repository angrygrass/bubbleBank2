package nl.hva.miw.c27.team1.cryptobanking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private double transactionValue;
    private double transactionCostPercentage;
    private double transactionCosts;
    private LocalDateTime dateTimeOfTransaction;
    private User buyer;
    private User seller;
    private Portfolio portfolio;
    private Asset asset;

    private final Logger logger = LogManager.getLogger(Transaction.class);

    public Transaction() {
        super();
        logger.info("New empty Transaction");
    }


}
