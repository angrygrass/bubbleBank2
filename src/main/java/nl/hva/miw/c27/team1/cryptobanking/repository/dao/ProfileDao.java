package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;

import java.util.Optional;

public interface ProfileDao {

    void save(Profile profile);
    Optional<Profile> findByUserName(String userName);
    Optional<Profile> findByUserId(int userId);

}
