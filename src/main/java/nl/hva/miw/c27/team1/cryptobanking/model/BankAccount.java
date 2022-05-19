package nl.hva.miw.c27.team1.cryptobanking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount {

    private String iban;
    private long balanceInEuros;
    private Portfolio portfolio;
    // or User?
    private Customer customer;

    private final Logger logger = LogManager.getLogger(BankAccount.class);

    public BankAccount(String iban, long balanceInEuros, Portfolio portfolio, Customer customer) {
        this.iban = iban;
        this.balanceInEuros = balanceInEuros;
        this.portfolio = portfolio;
        this.customer = customer;
    }

    public BankAccount(String iban) {
        this(iban,0,null,null);
    }

    public BankAccount() {
        this("empty");
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
