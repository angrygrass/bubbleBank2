package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;

import java.util.Optional;

public interface ProfileDao {

    Optional<Profile> findByUserName(String userName);

}
