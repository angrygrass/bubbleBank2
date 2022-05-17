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
    private User user;

    private final Logger logger = LogManager.getLogger(Portfolio.class);

    public Portfolio() {
        super();
        logger.info("New empty Portfolio");
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public Logger getLogger() {
        return logger;
    }
}
