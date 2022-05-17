package nl.hva.miw.c27.team1.cryptobanking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount {

    private String iban;
    private long balanceInEuros;
    private Portfolio portfolio;
    private User user;

    private final Logger logger = LogManager.getLogger(BankAccount.class);

    public BankAccount() {
        super();
        logger.info("New empty BankAccount");
    }

    public long getBalanceInEuros() {
        return balanceInEuros;
    }

    public void setBalanceInEuros(long balanceInEuros) {
        this.balanceInEuros = balanceInEuros;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Logger getLogger() {
        return logger;
    }
}
