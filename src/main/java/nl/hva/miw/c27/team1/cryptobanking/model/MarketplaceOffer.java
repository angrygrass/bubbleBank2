package nl.hva.miw.c27.team1.cryptobanking.model;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.MarketplaceDto;

import java.time.LocalDateTime;

public class MarketplaceOffer {

    private int offerId;
    private int userId;
    private LocalDateTime dateTime;
    private String assetCode;
    private double quantity;
    private double price;
    private boolean sellYesOrNo;
    private double transactionPrice;

    // userId is user who has made the offer, price is price for 1 unit of the asset, sellYesOrNo true = sell, false = buy


    public MarketplaceOffer(int offerId, int userId, LocalDateTime dateTime, String assetCode, double quantity, double price, boolean sellYesOrNo, double transactionPrice) {
        this.offerId = offerId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.assetCode = assetCode;
        this.quantity = quantity;
        this.price = price;
        this.sellYesOrNo = sellYesOrNo;
        this.transactionPrice = transactionPrice;
    }

    public MarketplaceOffer(MarketplaceDto marketplaceDto, double transactionPrice) {
        this.offerId = 0;
        this.userId = marketplaceDto.getUserId();
        this.dateTime = LocalDateTime.now();
        this.assetCode = marketplaceDto.getAssetCode();
        this.quantity = marketplaceDto.getQuantity();
        this.price = marketplaceDto.getPrice();
        this.sellYesOrNo = marketplaceDto.isSellYesOrNo();
        this.transactionPrice = transactionPrice;
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

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }
}
