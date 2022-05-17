package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(Transaction.class);


    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcUserDao.");
    }

    @Override
    public void save(User user) {
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }
}
