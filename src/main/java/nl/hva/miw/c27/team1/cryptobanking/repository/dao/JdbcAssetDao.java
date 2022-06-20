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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAssetDao implements AssetDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAssetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Asset asset) {
        String sql = "INSERT INTO asset(assetCode,assetName, rateInEuro) VALUES (?,?,?);";
        jdbcTemplate.update(sql, asset.getAssetCode(), asset.getAssetName(),
                asset.getRateInEuros());
    }
    @Override
    public void saveAllAssets(List<Asset> assetList) {
        String sql = "UPDATE asset SET assetName = ?, rateInEuro = ? WHERE assetCode = ?;";
        jdbcTemplate.update("update lastassetrateupdate set lastassetrateupdate = ?", LocalDateTime.now());
        for (Asset assets : assetList) {
            jdbcTemplate.update(sql, assets.getAssetName(), assets.getRateInEuros(), assets.getAssetCode());
        }

    }


    @Override
    public Optional<Asset> findByCode(String assetCode) {
        String sql = "SELECT * FROM asset WHERE assetCode = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new JdbcAssetDao.AssetRowMapper(), assetCode));
        } catch (EmptyResultDataAccessException e) {

            throw new InvalidAssetRequest(getInvalidAssetMsg());
        }
    }


    @Override
    public Optional<Asset> findByName(String name) {
        String sql = "SELECT * FROM asset WHERE assetName = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new AssetRowMapper(), name));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidAssetRequest(getInvalidAssetMsg());
        }

    }
    @Override
    public String getInvalidAssetMsg() {
        StringBuilder s = new StringBuilder("This is not a valid coin. Our coins are ");
        for (int i = 0; i < getAll().size(); i++) {
            s.append(getAll().get(i).getAssetName());
            if (i < getAll().size() - 1) {
                s.append(", ");
            }

        }

        return s.toString();
    }

    @Override
    public LocalDateTime getLastAssetRateUpdate() {
        String sql = "SELECT * FROM lastassetrateupdate;";
        return jdbcTemplate.queryForObject(sql, LocalDateTime.class);
    }


    @Override

    public List<Asset> getAll() {
        String sql = "SELECT * FROM asset;";
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
