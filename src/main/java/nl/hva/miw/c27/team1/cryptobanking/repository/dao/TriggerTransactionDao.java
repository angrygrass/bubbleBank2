package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.TriggerTransaction;

import java.util.List;
import java.util.Optional;

public interface TriggerTransactionDao {
    //todo jjs hier verder+
    Optional<TriggerTransaction> findByKey(String assetCode, int userId, boolean sellYesNo);
    void save(TriggerTransaction triggerTransaction);
    void delete(TriggerTransaction triggerTransaction);
    List<TriggerTransaction> getAll();
}
