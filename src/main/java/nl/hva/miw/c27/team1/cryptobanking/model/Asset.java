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

    public Asset(String assetName, String assetCode, double rateInEuros, Portfolio userPortfolio) {
        this.assetName = assetName;
        this.assetCode = assetCode;
        this.rateInEuros = rateInEuros;
        this.userPortfolio = userPortfolio;
        logger.info("New all-args Asset");
    }

    public Asset(String assetName, String assetCode, Double rateInEuros) {
        this(assetName,assetCode,rateInEuros, new Portfolio());
        logger.info("New Asset with 3 constructors");
    }

    public Asset(String assetCode) {
        this("",assetCode,0.0);
        logger.info("New Asset with assetCode");

    }

    public String getAssetCode() {
        return assetCode;
    }

    public double getRateInEuros() {
        return rateInEuros;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public void setRateInEuros(double rateInEuros) {
        this.rateInEuros = rateInEuros;
    }

    public void setUserPortfolio(Portfolio userPortfolio) {
        this.userPortfolio = userPortfolio;
    }

    public Portfolio getUserPortfolio() {
        return userPortfolio;
    }

    public Logger getLogger() {
        return logger;
    }
}
