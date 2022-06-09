package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionDto {
    private int buyerId;
    private int sellerId;
    private String assetCode;
    private double quantity;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TransactionDto.class);

    public TransactionDto(int buyerId, int sellerId, String assetCode, double quantity) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.assetCode = assetCode;
        this.quantity = quantity;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Logger getLogger() {
        return logger;
    }
}
