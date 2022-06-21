package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketplaceDto {
    int userId;
    String assetCode;
    double quantity;
    double price;
    boolean sellYesOrNo;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(MarketplaceDto.class);

    // userId is user who has made the offer, price is price for 1 unit of the asset, sellYesOrNo true = sell, false = buy

    public MarketplaceDto(int userId, String assetCode, double quantity, double price, boolean sellYesOrNo) {
        this.userId = userId;
        this.assetCode = assetCode;
        this.quantity = quantity;
        this.price = price;
        this.sellYesOrNo = sellYesOrNo;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
