package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.User;

import java.util.Optional;

public interface UserDao {

    void save(User user);

    Optional<User> findById(int id);


}
