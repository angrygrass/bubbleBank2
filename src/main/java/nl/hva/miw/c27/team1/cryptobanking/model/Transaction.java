package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
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

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(Transaction.class);

    public Transaction() {
        super();
        logger.info("New empty Transaction");
    }

    public Transaction(int transactionId, double quantity, double rateInEuro,
                       LocalDateTime dateTime, double transactionCosts,
                       int buyerId, int sellerId, String assetCode) {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public double getTransactionValue() {
        return transactionValue;
    }

    public double getTransactionCostPercentage() {
        return transactionCostPercentage;
    }

    public double getTransactionCosts() {
        return transactionCosts;
    }

    public LocalDateTime getDateTimeOfTransaction() {
        return dateTimeOfTransaction;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Asset getAsset() {
        return asset;
    }

    public Logger getLogger() {
        return logger;
    }
}
