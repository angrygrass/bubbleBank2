package nl.hva.miw.c27.team1.cryptobanking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Asset {

    private String assetName;
    private String assetCode;
    private double rateInEuros;
    private Portfolio userPortfolio;

    private final Logger logger = LogManager.getLogger(Asset.class);

    public Asset() {
        super();
        logger.info("New empty Asset");
    }

}
