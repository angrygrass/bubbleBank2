package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.model.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    void save(User user);

    Optional<User> findById(int id);

    Optional<List<User>> getAllUsers();

    Optional<User> findByToken(Token token);


}
