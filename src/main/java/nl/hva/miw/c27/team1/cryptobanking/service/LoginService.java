package nl.hva.miw.c27.team1.cryptobanking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {
    //todo jjs {STUB} define some object referring to token in the database
    //todo jjs {STUB} define some object referring to user in the database
    private final HashService hashService;

    //todo jjs {STUB} parameter list to be extended with types of private fields above

    @Autowired
    public LoginService(HashService hashService) {
        //todo jjs {STUB}{PSEUDO} this.someUserDatabaseObject  = someUserDatabaseObject;
        //todo jjs {STUB}{PSEUDO} this.someTokenDatabaseObject  = someTokenDatabaseObject;
        this.hashService = hashService;
    }

//    public LoginService() {
//        this.hashService = null;
//    }

    public String login(String username, String password) {
        String token = null;
        String hash = hashService.hash(password);
        //todo jjs {STUB} get hash stored in database with user
        String storedHash = "";

        if (hash.equals(storedHash)) {
            token = UUID.randomUUID().toString();
            //todo jjs {STUB} store token in database with user
            //todo jjs {PSEUDO} DAO.updateUser(User user, token)
        }
        ;
        return token;
    }
}
