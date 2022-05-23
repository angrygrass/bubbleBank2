package nl.hva.miw.c27.team1.cryptobanking.service;

import org.springframework.stereotype.Service;

/**
 * This class returns a pepper from a location that is not the database
 */
@Service
public class PepperService {
    private static final String PEPPER = "ThisShouldBeInASeparateLocation96542";

    public String getPepper() {
        return PEPPER;
    }
}
