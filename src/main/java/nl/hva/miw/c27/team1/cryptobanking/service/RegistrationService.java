package nl.hva.miw.c27.team1.cryptobanking.service;

import org.springframework.stereotype.Service;

/**
 * This class lets the user register for the first time.
 * Registration means that user data are stored on the database
 * once the user has submitted the required (data) fields
 */
@Service
public class RegistrationService {
    private final HashService hashService;

    //todo jjs add parameter referring to user (db or object?)
    public RegistrationService(HashService hashService) {
        //todo once parameter referring to user in db is added initialize it
        this.hashService = hashService;
    }

    //todo jjs {STUB} Registration is much more elaborate than just filling in name and password
    public void register(String username, String password) {
        // hash eerst natuurlijk
        String hash =  hashService.hash(password);
        //todo jjs insert username and hash into database (via controller > service ? repository > dao)
        //{STUB}
    }
}
