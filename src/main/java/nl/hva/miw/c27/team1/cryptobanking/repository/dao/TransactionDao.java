package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    Optional<Transaction> findById(int id);
    void save(Transaction transaction);
    List<Transaction> getAll();

}
