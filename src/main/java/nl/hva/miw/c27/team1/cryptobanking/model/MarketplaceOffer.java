package nl.hva.miw.c27.team1.cryptobanking.model;

import java.time.LocalDateTime;

public class MarketplaceOffer {

    private int offerId;
    private int userId;
    private LocalDateTime dateTime;
    private String assetCode;
    private double quantity;
    private double price;
    private boolean sellYesOrNo;


    public MarketplaceOffer(int offerId, int userId, LocalDateTime dateTime, String assetCode, double quantity, double price, boolean sellYesOrNo) {
        this.offerId = offerId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.assetCode = assetCode;
        this.quantity = quantity;
        this.price = price;
        this.sellYesOrNo = sellYesOrNo;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSellYesOrNo() {
        return sellYesOrNo;
    }

    public void setSellYesOrNo(boolean sellYesOrNo) {
        this.sellYesOrNo = sellYesOrNo;
    }
}
