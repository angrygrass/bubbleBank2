package nl.hva.miw.c27.team1.cryptobanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public abstract class User {

    private int id;
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
    private String role;


    private Profile profile;

    public User(int id, String role, String firstName, String prefix,
                String surName, int bsnNumber, Date birthDate, String streetName,
                String houseNumber, String zipCode, String residence, String country,
                Profile profile) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.prefix = prefix;
        this.surName = surName;
        this.bsnNumber = bsnNumber;
        this.birthDate = birthDate;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.residence = residence;
        this.country = country;
        this.profile = profile;
    }

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(User.class);

    public User(int id, String firstName, String prefix, String surName,
                 int bsnNumber, Date birthDate, String streetName, String houseNumber,
                 String zipCode, String residence, String country, Profile profile) {
        this(id, "Customer", firstName, prefix, surName, bsnNumber, birthDate, streetName, houseNumber, zipCode, residence, country,
        profile);
        logger.info("New private User");
    }

    public User(int id, String firstName, String prefix, String surName,
                int bsnNumber, Date birthDate, String streetName, String houseNumber,
                String zipCode, String residence, String country) {
        this(id, firstName, prefix, surName, bsnNumber, birthDate, streetName, houseNumber, zipCode,
                residence, country, null);

    }


    public User(String firstName, String prefix, String surName,
                int bsnNumber, Date birthDate, String streetName, String houseNumber,
                String zipCode, String residence, String country, Profile profile) {
        this(0, "Customer", firstName, prefix, surName, bsnNumber, birthDate, streetName, houseNumber,
                zipCode, residence, country, profile);

    }

    public User(int id, String role) {
        super();
        this.id = id;
        this.role = role;
        logger.info("New User with role");
    }

    public User(int id) {
        super();
        this.id = id;
        logger.info("New User with id");
    }

    public User() {
        super();
        logger.info("New empty User");
    }



    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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


        public Date getBirthDate () {
            return birthDate;
        }

        public void setBirthDate (Date birthDate){
            this.birthDate = birthDate;
        }

        public String getStreetName () {
            return streetName;
        }

        public void setStreetName (String streetName){
            this.streetName = streetName;
        }

        public String getHouseNumber () {
            return houseNumber;
        }

        public void setHouseNumber (String houseNumber){
            this.houseNumber = houseNumber;
        }

        public String getZipCode () {
            return zipCode;
        }

        public void setZipCode (String zipCode){
            this.zipCode = zipCode;
        }

        public String getResidence () {
            return residence;
        }

        public void setResidence (String residence){
            this.residence = residence;
        }

        public String getCountry () {
            return country;
        }

        public void setCountry (String country){
            this.country = country;
        }

        public Profile getProfile () {
            return profile;
        }

        public void setProfile (Profile profile){
            this.profile = profile;
        }


        public Logger getLogger () {
            return logger;
        }

        @Override
        public String toString () {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", surName='" + surName + '\'' +
                    '}';
        }
    }
