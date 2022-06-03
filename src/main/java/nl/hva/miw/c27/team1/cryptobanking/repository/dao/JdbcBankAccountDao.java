package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBankAccountDao implements BankAccountDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcBankAccountDao.class);
    private JdbcTemplate jdbcTemplate;
    private final double INITIAL_BALANCE = 5000.0;

    @Autowired
    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private PreparedStatement insertBankAccountStatement(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into `bank account` (IBAN, balanceInEuro, userId) values (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, INITIAL_BALANCE);
        ps.setInt(3, bankAccount.getCustomer().getId());

        return ps;
    }


    //Bank Account ??? is there space between Bank and account
    public Optional<BankAccount> findById(int id) {
        String sql = "SELECT * FROM `bank account` WHERE userId = ?;";
        try {
            return Optional.of(this.jdbcTemplate.queryForObject(sql,new BankAccountRowMapper(),id));
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
            return null;
        }
    }

    public void save(BankAccount bankAccount) {
        jdbcTemplate.update(connection -> insertBankAccountStatement(bankAccount, connection));
    }

    public List<BankAccount> getAll() {
        String sql = "SELECT * FROM `bank account`;";
        try{
            return jdbcTemplate.query(sql, new BankAccountRowMapper());
        }catch(EmptyResultDataAccessException e){
            e.getMessage();
            return null;
        }
    }

    public void updateBalance(BankAccount bankAccount) {
        String sql = "UPDATE `bank account` SET balanceInEuro = ? WHERE userId = ?;";
        jdbcTemplate.update(sql, bankAccount.getBalanceInEuros(),
                bankAccount.getCustomer().getId());
    }

    public void deleteBankAccountByUserId(int id) {
        String sql = "DELETE FROM `bank account` WHERE userId = ?;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<BankAccount> findByIban(String Iban) {
        String sql = "SELECT * FROM `bank account` WHERE IBAN = ?;";
        try{
            return Optional.of(jdbcTemplate.queryForObject(sql, new BankAccountRowMapper()));
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
