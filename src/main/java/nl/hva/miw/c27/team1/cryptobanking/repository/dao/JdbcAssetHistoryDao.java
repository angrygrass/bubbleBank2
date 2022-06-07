package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcAssetHistoryDao implements AssetHistoryDao {

    private JdbcTemplate jdbcTemplate;
    private LocalDateTime currentDate;

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


}

