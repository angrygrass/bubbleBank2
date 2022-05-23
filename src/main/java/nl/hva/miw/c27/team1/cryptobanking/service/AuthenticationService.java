package nl.hva.miw.c27.team1.cryptobanking.service;


import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.service.HashService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class offers authentication either by logging on or by presenting the correct token
 */
@Service
public class AuthenticationService {

    private RootRepository rootRepository;
    private final Logger logger = LogManager.getLogger(AuthenticationService.class);

    //todo jjs {STUB} define some object referring to token in the database
    //todo jjs {STUB} define some object referring to user in the database
    private final HashService hashService;

    //todo jjs {STUB} parameter list to be extended with types of private fields above
    @Autowired
    public AuthenticationService(HashService hashService) {
        //todo jjs {STUB}{PSEUDO} this.someUserDatabaseObject  = someUserDatabaseObject;
        //todo jjs {STUB}{PSEUDO} this.someTokenDatabaseObject  = someTokenDatabaseObject;
        this.hashService = hashService;
    }

    /**
     *
     * @param username speaks for itself
     * @param password speaks for itself
     * @return success if username and digest match with database
     */
    boolean authenticate(String username, String password) {
        String hash = hashService.hash(password);
        //todo jjs {STUB} get hash stored in database with user
        String storedHash = "";

        return hash.equals(storedHash);
    }

    //overloaded version with token instead of username password
    public boolean authenticate(String token) {
        //possibly  check UUID
        //ENSURE token exists?
        //todo jjs hier verder
        //check if token exists in db at all (or for that specific user)?
        //todo {PSEUDO} user = DAO.findUserByToken; if (user != null) return true;
        return true;
    }

}
