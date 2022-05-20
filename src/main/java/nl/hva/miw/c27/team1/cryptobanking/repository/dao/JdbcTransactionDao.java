package nl.hva.miw.c27.team1.cryptobanking.repository.dao;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class JdbcTransactionDao implements TransactionDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcTransactionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Override
    public Transaction findById(int id) {
        String sql = "SELECT * FROM TransActionHistory WHERE transactionId = ?;";
        try {
            return this.jdbcTemplate.queryForObject(sql, new JdbcTransactionDao.TransactionRowMapper(), id);
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int save(Transaction transaction) {
        String sql = "INSERT INTO TransationHistory(transactionId,quantity, rateInEuro,dateTime,transactionCosts,buyerId,sellerId,assetCode)" +
                " VALUES (?,?,?,?,?,?,?,?);";
        return jdbcTemplate.update(sql,transaction.getTransactionId(),transaction.getTransactionValue(),transaction.getTransactionCostPercentage(),
        transaction.getDateTimeOfTransaction(),transaction.getTransactionCosts(),transaction.getBuyer().getId(),transaction.getSeller().getId(),
                transaction.getAsset().getAssetCode()
                );
    }

    //Seller or Buyer histories ???
    @Override
    public List<Transaction> getAll() {
        String sql = "SELECT * FROM TransActionHistory ;";
        try{
            return this.jdbcTemplate.query(sql, new JdbcTransactionDao.TransactionRowMapper());
        }catch(DataAccessException e){
           e.getMessage();
           return null;
        }
    }

    @Override
    public int updateOne(Transaction transaction) {
        return 0;
    }

    //Is this method necessary??????????
    @Override
    public int deleteOne(int id) {
        String sql = "DELETE FROM TransActionHistory WHERE  transactionId= ?;";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public LocalDateTime findByDateTimeOfTransaction(LocalDateTime time) {
       return null;
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
