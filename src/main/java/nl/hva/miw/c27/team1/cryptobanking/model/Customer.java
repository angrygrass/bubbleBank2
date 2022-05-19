package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;

public class Customer extends User {

    private BankAccount bankAccount;
    private Portfolio portfolio;
    private List<Transaction> transactionList;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Customer.class);

    public Customer(String firstName, String prefix, String surName, int bsnNumber, LocalDate birthDate,
                    String streetName, String houseNumber, String zipCode, String residence, String country,
                    Profile profile, BankAccount bankAccount, Portfolio portfolio,
                    List<Transaction> transactionList) {
        super(firstName, prefix, surName, bsnNumber, birthDate, streetName, houseNumber, zipCode, residence,
                country, profile);
        this.bankAccount = bankAccount;
        this.portfolio = portfolio;
        this.transactionList = transactionList;
    }

    public Customer(int id, String role) {
        super(id, role);
        logger.info("New Customer met 2 attributen");
    }

    public Customer(CustomerDto dto) {
        this(dto.getFirstName(),dto.getPrefix(),dto.getSurName(),dto.getBsnNumber(),dto.getBirthDate(),
                dto.getStreetName(),dto.getHouseNumber(),dto.getZipCode(),dto.getResidence(),dto.getCountry(),
                dto.getProfile(),dto.getBankAccount(),dto.getPortfolio(),dto.getTransactionList());
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
