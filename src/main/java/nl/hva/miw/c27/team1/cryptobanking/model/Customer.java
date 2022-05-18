package nl.hva.miw.c27.team1.cryptobanking.model;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Customer extends User {

    private final Logger logger = LoggerFactory.getLogger(Customer.class);

    public Customer(String firstName, String prefix, String surName, int bsnNumber, Date birthDate,
                    String streetName, String houseNumber, String zipCode, String residence, String country, Profile profile) {
        super(firstName, prefix, surName, bsnNumber, birthDate, streetName, houseNumber, zipCode, residence, country, profile);
    }

    public Customer(int id, String role) {
        super(id, role);
        logger.info("New Customer met 2 attributen");
    }


}
