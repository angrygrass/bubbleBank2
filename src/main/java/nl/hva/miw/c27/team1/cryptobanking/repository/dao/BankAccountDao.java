package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import java.util.List;
import java.util.Optional;

public interface BankAccountDao {

    Optional<BankAccount> findById(int id);
    void save(BankAccount bankAccount);
    List<BankAccount> getAll();
    void updateBalance(BankAccount bankAccount);
    void deleteBankAccountByUserId(int id);
    Optional<BankAccount> findByIban(String Iban);



}
