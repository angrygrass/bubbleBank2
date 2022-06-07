package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuyAssetDto {
    private int userid;
    private String assetCode;
    private double quantity;


    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(BuyAssetDto.class);

    public BuyAssetDto(int userid, String assetCode, double quantity) {
        this.userid = userid;
        this.assetCode = assetCode;
        this.quantity = quantity;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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
