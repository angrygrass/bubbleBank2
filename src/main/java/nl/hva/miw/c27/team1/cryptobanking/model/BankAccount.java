package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount {

    private String iban;
    private double balanceInEuros;
    private Customer customer;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(BankAccount.class);

    public BankAccount(String iban, double balanceInEuros, Customer customer) {
        this.iban = iban;
        this.balanceInEuros = balanceInEuros;
        this.customer = customer;
    }

    public BankAccount(String iban) {
        this(iban, 0, null);
    }

    public BankAccount() {
        this("empty");
        logger.info("New empty BankAccount");
    }

    public BankAccount(String iban, double balanceInEuros) {
        this.iban = iban;
        this.balanceInEuros = balanceInEuros;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;

    }

    public double getBalanceInEuros() {
        return balanceInEuros;
    }

    public void setBalanceInEuros(double balanceInEuros) {
        this.balanceInEuros = balanceInEuros;
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
