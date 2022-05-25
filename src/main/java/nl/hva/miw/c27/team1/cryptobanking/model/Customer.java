package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.*;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Annotation handles circular references (e.g. bi-directional relationships between
 * classes). Spring serializes the back-reference rahter than the whole reference.
 * in this case, the back reference is identied by the 'id' property
 */
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class Customer extends User {

    private BankAccount bankAccount;
    private Portfolio portfolio;
    private List<Transaction> transactionList;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Customer.class);


    public Customer(int id, String firstName, String prefix, String surName, int bsnNumber, Date birthDate,
                    String streetName, String houseNumber, String zipCode, String residence, String country,
                    Profile profile, BankAccount bankAccount, Portfolio portfolio,
                    List<Transaction> transactionList) {
        super(id, firstName, prefix, surName, bsnNumber, birthDate, streetName, houseNumber, zipCode, residence,
                country, profile);
        this.bankAccount = bankAccount;
        this.portfolio = portfolio;
        this.transactionList = transactionList;
    }

    public Customer(int id, String firstName, String preFix,String surName,int bsnNumber,Date birthDate,
                    String streetName,String houseNumber, String zipCode,String residence,String country) {
        this(id,firstName,preFix,surName,bsnNumber,birthDate,streetName,houseNumber,zipCode,residence,country,
                new Profile(), new BankAccount(),new Portfolio(),new ArrayList<>());
    }

    public Customer(RegisterDto dto) {
        this(0, dto.getFirstName(),dto.getPrefix(),dto.getSurName(),dto.getBsnNumber(),dto.getBirthDate(),
                dto.getStreetName(),dto.getHouseNumber(),dto.getZipCode(),dto.getResidence(),dto.getCountry(),
                new Profile(), new BankAccount(), new Portfolio(), new ArrayList<>());
        this.setBankAccount(new BankAccount(dto.getIban(),5000, new Portfolio(), this));
        this.setProfile(new Profile(dto.getUserName(), dto.getPassWord(), this));
    }

    public Customer(int id, String role) {
        super(id, role);
        logger.info("New Customer met 2 attributen voor mock ArrayLijst");
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
