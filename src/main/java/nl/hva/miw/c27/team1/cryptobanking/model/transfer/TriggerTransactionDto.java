package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerTransactionDto {

    private String assetCode;
    private int userId;
    private boolean sellYesNo;
    private double triggerRate;
    private double quantityToBuyOrSell;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TriggerTransactionDto.class);

    public TriggerTransactionDto(String assetCode, int userId, boolean sellYesNo, double triggerRate, double quantityToBuyOrSell) {
        this.assetCode = assetCode;
        this.userId = userId;
        this.sellYesNo = sellYesNo;
        this.triggerRate = triggerRate;
        this.quantityToBuyOrSell = quantityToBuyOrSell;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isSellYesNo() {
        return sellYesNo;
    }

    public void setSellYesNo(boolean sellYesNo) {
        this.sellYesNo = sellYesNo;
    }

    public double getTriggerRate() {
        return triggerRate;
    }

    public void setTriggerRate(double triggerRate) {
        this.triggerRate = triggerRate;
    }

    public double getQuantityToBuyOrSell() {
        return quantityToBuyOrSell;
    }

    public void setQuantityToBuyOrSell(double quantityToBuyOrSell) {
        this.quantityToBuyOrSell = quantityToBuyOrSell;
    }
}
