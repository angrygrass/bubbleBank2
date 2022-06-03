package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Portfolio {

    private double valueOfOwnedAssets;
    private String currencyPreference;
    private Map<Asset, Double> assetsOfUser;
    private Customer customer;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(Portfolio.class);

    public Portfolio(String currencyPreference, Map<Asset, Double> assetsOfUser, Customer customer) {
        this.currencyPreference = currencyPreference;
        this.assetsOfUser = assetsOfUser;
        this.customer = customer;
        logger.info("New complete Portfolio");
    }

    public Portfolio(Map<Asset, Double> assetsOfUser, Customer customer) {
        this("EUR", assetsOfUser, customer);
        logger.info("New Portfolio with HashMap");
    }

    public Portfolio() {

        this(new HashMap<>(), new Customer());



        logger.info("New empty Portfolio");
    }

    // getters & setters


    public double getValueOfOwnedAssets() {
        return valueOfOwnedAssets;
    }

    public void setValueOfOwnedAssets(double valueOfOwnedAssets) {
        this.valueOfOwnedAssets = valueOfOwnedAssets;
    }

    public String getCurrencyPreference() {
        return currencyPreference;
    }

    public void setCurrencyPreference(String currencyPreference) {
        this.currencyPreference = currencyPreference;
    }


    public Map<Asset, Double> getAssetsOfUser() {



        return assetsOfUser;
    }

    public void setAssetsOfUser(HashMap<Asset, Double> assetsOfUser) {
        this.assetsOfUser = assetsOfUser;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "valueOfOwnedAssets=" + valueOfOwnedAssets +
                ", currencyPreference='" + currencyPreference + '\'' +
                ", assetsOfUser=" + assetsOfUser +
                ", customer=" + customer +
                ", logger=" + logger +
                '}';
    }
}
