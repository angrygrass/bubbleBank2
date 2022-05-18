package nl.hva.miw.c27.team1.cryptobanking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount {

    private String iban;
    private long balanceInEuros;
    private Portfolio portfolio;
    private Customer customer;

    private final Logger logger = LogManager.getLogger(BankAccount.class);

    public BankAccount() {
        super();
        logger.info("New empty BankAccount");
    }

    // getters & setters


    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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
