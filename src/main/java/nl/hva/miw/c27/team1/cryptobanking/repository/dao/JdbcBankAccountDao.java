package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcBankAccountDao implements BankAccountDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcBankAccountDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Bank Account ??? is there space between Bank and account
    public BankAccount findById(int id) {
        String sql = "SELECT * FROM Bank Account WHERE userId = ?;";
        try {
            return this.jdbcTemplate.queryForObject(sql,new BankAccountRowMapper(),id);
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
            return null;
        }
    }

    public int save(BankAccount bankAccount) {
        String sql = "INSERT INTO Bank Account(IBAN, balanceInEuro, userId) VALUES (?,?,?);";
        return jdbcTemplate.update(sql,bankAccount.getIban(),bankAccount.getBalanceInEuros(),
                bankAccount.getCustomer().getId());
    }

    public List<BankAccount> getAll() {
        String sql = "SELECT * FROM Bank Account;";
        try{
            return jdbcTemplate.query(sql, new BankAccountRowMapper());
        }catch(EmptyResultDataAccessException e){
            e.getMessage();
            return null;
        }
    }

    public int updateOne(BankAccount bankAccount) {
        String sql = "UPDATE Bank Account SET IBAN = ?, balanceInEuro = ? WHERE userId = ?;";
        return jdbcTemplate.update(sql, bankAccount.getIban(),bankAccount.getBalanceInEuros(),
                bankAccount.getCustomer().getId());
    }

    public int deleteOne(int id) {
        String sql = "DELETE FROM Bank Account WHERE userId = ?;";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public BankAccount findByIban(String Iban) {
        String sql = "SELECT * FROM Bank Account WHERE IBAN = ?;";
        try{
            return jdbcTemplate.queryForObject(sql, new BankAccountRowMapper());
        }catch(DataAccessException e){
            e.getMessage();
            return null;
        }
    }

    //????? Do we need to add userId into new BankAccount() ?
    private static class BankAccountRowMapper implements RowMapper<BankAccount> {
        @Override
        public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BankAccount(rs.getString("IBAN"), rs.getLong("balanceInEuro"));
        }
    }








}
