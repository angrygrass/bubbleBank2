package nl.hva.miw.c27.team1.cryptobanking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class Portfolio {

    private double valueOfOwnedAssets;
    private String currencyPreference;
    private List<Asset> assetsOfUser;
    private List<Transaction> transactionsOfUser; // correct/juiste plaats?
    private BankAccount bankAccount;
    private Customer customer;

    private final Logger logger = LogManager.getLogger(Portfolio.class);

    public Portfolio() {
        super();
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

    public List<Asset> getAssetsOfUser() {
        return assetsOfUser;
    }

    public void setAssetsOfUser(List<Asset> assetsOfUser) {
        this.assetsOfUser = assetsOfUser;
    }

    public List<Transaction> getTransactionsOfUser() {
        return transactionsOfUser;
    }

    public void setTransactionsOfUser(List<Transaction> transactionsOfUser) {
        this.transactionsOfUser = transactionsOfUser;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
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
}
