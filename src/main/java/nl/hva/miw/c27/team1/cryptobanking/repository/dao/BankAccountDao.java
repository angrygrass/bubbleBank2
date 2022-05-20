package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import java.util.List;

public interface BankAccountDao {

    public BankAccount findById(int id);
    public int save(BankAccount bankAccount);
    public List<BankAccount> getAll();
    public int updateOne(BankAccount bankAccount);
    public int deleteOne(int id);
    public BankAccount findByIban(String Iban);



}
