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

@Repository
public class JdbcAssetDao implements AssetDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAssetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Asset asset) {
        String sql = "INSERT INTO Asset(assetCode,assetName, rateInEuro) VALUES (?,?,?);";
        return jdbcTemplate.update(sql, asset.getAssetCode(), asset.getAssetName(),
                asset.getRateInEuros());
    }

    @Override
    public Asset findByCode(String assetCode) {
        String sql = "SELECT * FROM Asset WHERE assetCode = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new JdbcAssetDao.AssetRowMapper(), assetCode);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return null;
        }
    }


    @Override
    public Asset findByName(String name) {
        String sql = "SELECT * FROM Asset WHERE assetName = ?;";
        return jdbcTemplate.queryForObject(sql, new AssetRowMapper(), name);
    }

    @Override
    public List<Asset> getAll() {
        String sql = "SELECT * FROM Asset;";
        return jdbcTemplate.query(sql, new AssetRowMapper());
    }


    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Asset(rs.getString("assetCode"),
                    rs.getString("assetName"), rs.getDouble("rateInEuro"));
        }
    }

}
