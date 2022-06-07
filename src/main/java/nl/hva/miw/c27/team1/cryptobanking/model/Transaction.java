package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private double quantity;
    private double rateInEuro;

    private LocalDateTime dateTime;
    private double transactionCosts;
    private int buyerId;
    private int sellerId;
    private String assetCode;
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

    public Transaction(int transactionId, double quantity, double rateInEuro, LocalDateTime dateTime,
                       double transactionCosts, int buyerId, int sellerId, String assetCode) {
        this.transactionId = transactionId;
        this.quantity = quantity;
        this.rateInEuro = rateInEuro;
        this.dateTime = dateTime;
        this.transactionCosts = transactionCosts;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.assetCode = assetCode;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRateInEuro() {
        return rateInEuro;
    }

    public void setRateInEuro(double rateInEuro) {
        this.rateInEuro = rateInEuro;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getTransactionCosts() {
        return transactionCosts;
    }

    public void setTransactionCosts(double transactionCosts) {
        this.transactionCosts = transactionCosts;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }
}
