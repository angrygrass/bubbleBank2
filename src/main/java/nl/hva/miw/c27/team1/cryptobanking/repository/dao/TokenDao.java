package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.model.User;

import java.util.Optional;

public interface TokenDao {
    void save(Token token);
    Optional<Token> findByUserId(int userId);
    void revokeToken(User user);

    public boolean checkIfExistsValidTokenForUser(User user);

}
