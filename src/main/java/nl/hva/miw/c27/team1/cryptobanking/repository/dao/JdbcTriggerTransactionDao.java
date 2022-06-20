package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.TriggerTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTriggerTransactionDao implements TriggerTransactionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTriggerTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTriggerTransactionDao() {
        super();
    }

    @Override
    public Optional<TriggerTransaction> findByKey(String assetCode, int userId, boolean sellYesNo) {
        String sql = "SELECT * FROM triggertransaction WHERE assetCode = ? AND userId = ? AND sellYesNo = ?";
        List<TriggerTransaction> triggerTransactions = jdbcTemplate.query(sql, new LocalRowMapper(), assetCode,
                userId, sellYesNo);
        if (triggerTransactions.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(triggerTransactions.get(0));
        }
    }

    @Override
    public void save(TriggerTransaction triggerTransaction) {
        jdbcTemplate.update(connection -> insertTriggerTransaction(triggerTransaction, connection));
    }

    private PreparedStatement insertTriggerTransaction(TriggerTransaction triggerTransaction,
                                                       Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into cryptobank.`triggertransaction` (assetCode, userId," +
                        " sellYesNo, triggerRate, quantityToBuyOrSell) values (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, triggerTransaction.getAssetCode());
        ps.setInt(2, triggerTransaction.getUserId());
        ps.setBoolean(3, triggerTransaction.isSellYesNo());
        ps.setDouble(4, triggerTransaction.getTriggerRate());
        ps.setDouble(5, triggerTransaction.getQuantityToBuyOrSell());

        return ps;
    }

    @Override
    public void delete(TriggerTransaction triggerTransaction) {
        String sql = "DELETE FROM triggertransaction WHERE assetCode = ? AND userId = ? AND sellYesNo = ?";
        jdbcTemplate.update(sql, triggerTransaction.getAssetCode(), triggerTransaction.getUserId(),
                triggerTransaction.isSellYesNo());
    }

    @Override
    public List<TriggerTransaction> getAll() {
        String sql = "SELECT * FROM triggertransaction ;";
        try {
            return this.jdbcTemplate.query(sql, new JdbcTriggerTransactionDao.LocalRowMapper());
        } catch(DataAccessException e) {
            e.getMessage();
            return null;
        }
    }

    private static class LocalRowMapper implements RowMapper<TriggerTransaction> {
        @Override
        public TriggerTransaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            String assetCode = resultSet.getString("assetCode");
            int userId = resultSet.getInt("userId");
            boolean sellYesNo = resultSet.getBoolean("sellYesNo");
            double triggerRate = resultSet.getDouble("triggerRate");
            double quantityToBuyOrSell = resultSet.getDouble("quantityToBuyOrSell");
            return new TriggerTransaction(assetCode, userId, sellYesNo, triggerRate, quantityToBuyOrSell);
        }
    }
}
