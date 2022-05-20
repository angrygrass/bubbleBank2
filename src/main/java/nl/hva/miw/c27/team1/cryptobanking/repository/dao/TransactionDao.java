package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDao {
    public Transaction findById(int id);
    public int save(Transaction transaction);
    public List<Transaction> getAll();
    public int updateOne(Transaction transaction);
    public int deleteOne(int id);
    public LocalDateTime findByDateTimeOfTransaction(LocalDateTime time);
}
