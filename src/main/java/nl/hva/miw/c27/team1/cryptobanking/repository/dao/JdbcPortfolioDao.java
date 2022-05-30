package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
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
public class JdbcPortfolioDao implements PortfolioDao {

    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public double findQuantityOfAssetInPortfolio (String assetCode, int userId) {
        String sql = "SELECT * FROM assetofcustomer WHERE assetCode = ? AND userId = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, Double.class, assetCode, userId);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return 0.0;
        }
    }





    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Asset(rs.getString("assetCode"),
                    rs.getString("assetName"), rs.getDouble("rateInEuro"));
        }
    }
}
