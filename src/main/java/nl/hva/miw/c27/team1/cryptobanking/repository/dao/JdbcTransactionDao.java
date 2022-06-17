package nl.hva.miw.c27.team1.cryptobanking.repository.dao;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidAssetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTransactionDao implements TransactionDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcTransactionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;

        logger.info("New JdbcTokenDao.");
    }

    @Override
    public Optional<Transaction> findById(int id) {
        String sql = "SELECT * FROM transactionhistory WHERE transactionId = ?;";
        try {
            return Optional.of(this.jdbcTemplate.queryForObject(sql, new JdbcTransactionDao.TransactionRowMapper(), id));
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int save(Transaction transaction) {
        String sql = "INSERT INTO transactionhistory(quantity, rateInEuro,dateTime,transactionCosts,buyerId,sellerId,assetCode)" +
                " VALUES (?,?,?,?,?,?,?);";

        jdbcTemplate.update(sql,transaction.getQuantity(),transaction.getRateInEuro(),
        transaction.getDateTime(),transaction.getTransactionCosts(),transaction.getBuyerId(),transaction.getSellerId(),
                transaction.getAssetCode()
                );

        sql = "SELECT transactionId FROM transactionhistory order by `dateTime` desc limit 1;";

        Integer transactionId = jdbcTemplate.queryForObject(sql, Integer.class);
        if (transactionId == null) {
            return 0;
        } else {
            return transactionId;
        }
    }

    //Seller or Buyer histories ???
    @Override
    public List<Transaction> getAll() {
        String sql = "SELECT * FROM transactionhistory ;";
        try{
            return this.jdbcTemplate.query(sql, new JdbcTransactionDao.TransactionRowMapper());
        }catch(DataAccessException e){
           e.getMessage();
           return null;
        }
    }




    private static class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Transaction(rs.getInt("transactionId"),
                    rs.getDouble("quantity"),
                    rs.getDouble("rateInEuro"),
                    LocalDateTime.parse(rs.getString("datetime"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    rs.getDouble("transactionCosts"),
                    rs.getInt("buyerId"),
                    rs.getInt("sellerId"),
                    rs.getString("assetCode"));
        }
    }



}
