package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Asset {

    private String assetName;
    private String assetCode;
    private double rateInEuros;
    private Portfolio userPortfolio;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(Asset.class);

    public Asset(String ac, String an, Double re) {
        super();
        logger.info("New empty Asset");
    }

    public String getAssetName() {
        return assetName;
    }

    public Asset(String assetName, String assetCode, double rateInEuros, Portfolio userPortfolio) {
        this.assetName = assetName;
        this.assetCode = assetCode;
        this.rateInEuros = rateInEuros;
        this.userPortfolio = userPortfolio;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public double getRateInEuros() {
        return rateInEuros;
    }

    public Portfolio getUserPortfolio() {
        return userPortfolio;
    }

    public Logger getLogger() {
        return logger;
    }
}
