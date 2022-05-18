package nl.hva.miw.c27.team1.cryptobanking.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Admin extends User {

    private int staffId;

    private final Logger logger = LoggerFactory.getLogger(Admin.class);

    public Admin(String firstName, String prefix, String surName, int bsnNumber, Date birthDate,
                 String streetName, String houseNumber, String zipCode, String residence, String country, Profile profile,
                 int staffId) {
        super(firstName, null, surName, bsnNumber, birthDate, streetName, houseNumber, zipCode, residence, country, profile);
        this.staffId = staffId;
    }

    public Admin(int id, String role) {
        super(id, role);
        logger.info("New Admin with 2 attributes");
    }


}
