package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;

/*
A Dto class limits the amount of sensitive data send. Only provided what is needed.
In Java applications - entity classes are used to represent tables in a relational database. Without DTOs,
we'd have to expose the entire entities to a remote interface. This causes a strong coupling between
an API and a persistence model.
*/
public class RegisterDto {

    private int userId;
    private String firstName;
    private String prefix;
    private String surName;
    private int bsnNumber;
    private Date birthDate;
    private String streetName;
    private String houseNumber;
    private String zipCode;
    private String residence;
    private String country;
    private String userName;
    private String passWord;
    private Profile profile;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RegisterDto.class);

    public RegisterDto(Customer customer) {
        super();
        this.userId = customer.getId();
        this.firstName = customer.getFirstName();
        this.prefix = customer.getPrefix();
        this.surName = customer.getSurName();
        this.bsnNumber = customer.getBsnNumber();
        this.birthDate = customer.getBirthDate();
        this.streetName = customer.getStreetName();
        this.houseNumber = customer.getHouseNumber();
        this.zipCode = customer.getZipCode();
        this.residence = customer.getResidence();
        this.country = customer.getCountry();
        this.userName = customer.getProfile().getUserName();
        this.passWord = customer.getProfile().getPassWord();
        logger.info("New CustomerDto using all-args");
    }

    public RegisterDto() {
        super();
        logger.info("New empty CustomerDto");
    }

    // getters & setters


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getBsnNumber() {
        return bsnNumber;
    }

    public void setBsnNumber(int bsnNumber) {
        this.bsnNumber = bsnNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Logger getLogger() {
        return logger;
    }
}
