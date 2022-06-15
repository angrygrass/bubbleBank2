package nl.hva.miw.c27.team1.cryptobanking.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.TokenRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authorization.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

/**
 * This class offers authentication either by logging on or by presenting the correct token
 */
@Service
public class AuthenticationService {

    private final Logger logger = LogManager.getLogger(AuthenticationService.class);

    final private RootRepository rootRepository;

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(RootRepository rootRepository, TokenRepository tokenRepository) {
        this.rootRepository = rootRepository;
        this.tokenRepository = tokenRepository;
        logger.info("new AuthenticationService");
    }

    public String createToken() {
        return UUID.randomUUID().toString();
    }

    public Timestamp getTimestampNowWithAddedMinutes(int minutes) {
        final int MILLISECONDS_IN_MINUTE = 60000;
        long datetime = System.currentTimeMillis();
        datetime = datetime + (long) minutes * MILLISECONDS_IN_MINUTE;
        return new Timestamp(datetime);
        //System.out.println("Current Time Stamp: "+ timestamp);
    }

    public Profile validateLogin(String userName, String hashedPassWord) {
        Optional<Profile> optionalProfile = rootRepository.getProfileByUsername(userName);
        Profile profile = optionalProfile.orElse(null);
        if (profile != null && profile.getHash().equals(hashedPassWord)) {
            return profile;
        } else {
            return null;
        }
    }

    public boolean validateToken(String strToken, User user) {
        //check if token exists in db at all (or for that specific user)?
        Token token = tokenRepository.findByUserId(user.getId()).orElse(null);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        //token should be equal to token in database and still be valid
        return token != null && token.getTokenId().equals(strToken) && token.getValiduntil().after(now);
    }

    public Optional<Token> getTokenByUserId(int userId) {return rootRepository.getTokenByUserId(userId);}


    public void save(Token token) {
        tokenRepository.save(token);
    }

    public void revokeTokenFromUser(User user) {
        if (!user.getRole().isEmpty() && user.getRole().equals(Role.Admin.name())) {
            rootRepository.revokeTokenFromUser(user);
        }
    }

    public boolean checkIfExistsValidTokenForUser(User user) {
        return rootRepository.checkIfExistsValidTokenForUser(user);
    }

    public String extractTokenFromBearer(String bearerToken) {
        final String BEARER = "Bearer";
        String token = "";
        if (bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length()).trim();
        }
        return token;
    }

    public ObjectNode getJsonObjectForToken(String tokenID) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("token", tokenID);
        return result;
    }

}
