package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Dto required to save asset history in the database.
 */
public class AssetHistoryDto {

    private LocalDate dateTime;
    @JsonProperty("symbol")
    private String assetCode;
    @JsonProperty("current_price")
    private double rate;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AssetHistoryDto.class);

    public AssetHistoryDto(LocalDate dateTime, String assetCode, double rate) {
        this.dateTime = dateTime;
        this.assetCode = assetCode;
        this.rate = rate;
        logger.info("New AssetHistoryDto using all-args");
    }

    public AssetHistoryDto() {
        logger.info("New empty AssetHistoryDto");
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "AssetHistoryDto{" +
                "dateTime=" + dateTime +
                ", assetCode='" + assetCode + '\'' +
                ", rate=" + rate +
                '}';
    }
}
