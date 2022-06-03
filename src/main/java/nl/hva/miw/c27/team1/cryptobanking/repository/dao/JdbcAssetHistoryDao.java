package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcAssetHistoryDao implements AssetHistoryDao {

    private JdbcTemplate jdbcTemplate;
    public LocalDateTime currentDate;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    @Autowired
    public JdbcAssetHistoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAssetHistoryList(List<AssetHistoryDto> assetHistoryList) {
        currentDate = LocalDateTime.now();
        try {
            String sql = "INSERT INTO assetratehistory (dateTime, assetCode, rate) VALUES (?, ?, ?)";
            for (AssetHistoryDto history : assetHistoryList) {
                jdbcTemplate.update(sql, currentDate, history.getAssetCode(), history.getRate());
            }
        } catch (DataIntegrityViolationException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public Optional<List> getAllHistoricAssets(String assetCode, int numberDays) {
        var currentDate = LocalDate.now();
        var dateMinus = currentDate.minusDays(numberDays);
        var sqlCurrentDate = java.sql.Date.valueOf(currentDate);
        var sqlDateMinus = java.sql.Date.valueOf(dateMinus);
        String sql = "select * FROM assetratehistory WHERE assetCode = '" + assetCode + "' AND dateTime BETWEEN '" + sqlDateMinus + "' AND '" +
                sqlCurrentDate + "';";
        List<AssetHistoryDto> assetHistoryDtoList = jdbcTemplate.query
                (sql, new JdbcAssetHistoryDao.HistoricAssetRowMapper());
        return Optional.of(assetHistoryDtoList);
    }

    private static class HistoricAssetRowMapper implements RowMapper<AssetHistoryDto> {
        @Override
        public AssetHistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AssetHistoryDto(rs.getObject(1, LocalDate.class),
                    rs.getString("assetCode"), rs.getDouble("rate"));
        }
    }


}

