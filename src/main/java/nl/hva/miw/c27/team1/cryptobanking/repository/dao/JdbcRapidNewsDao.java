package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RapidNewsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JdbcRapidNewsDao implements RapidNewsDao {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcRapidNewsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcNewsDao.");
    }

    @Override
    public void saveArticles(List<RapidNewsDto> newsDto) {

    }

    /*@Override
    public void saveArticles(List<RapidNewsDto> newsDtos) {
        try {
            String sql = "UPDATE article_link SET article_link = ? WHERE id = ?;";
            for (RapidNewsDto articleLink : newsDtos) {
                jdbcTemplate.update(sql, articleLink.getUrl(), articleLink.getId());
            }
        } catch (DataIntegrityViolationException e) {
            logger.info(e.getMessage());
        }
    }*/

}
