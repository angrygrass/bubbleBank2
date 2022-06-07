package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidAssetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTransactionCostsDao implements TransactionCostsDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcTransactionCostsDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransactionCostsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void edit(double transactionCosts) {
        String sql = "update transactioncosts set transactioncosts = ?";
        jdbcTemplate.update(sql, transactionCosts);
    }

    @Override
    public double get() {
        String sql = "SELECT * FROM transactioncosts;";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }



}
