package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProfileDao implements ProfileDao {

    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(JdbcProfileDao.class);
    private JdbcUserDao jdbcUserDao;


    @Autowired
    public JdbcProfileDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;

        logger.info("New JdbcProfileDao.");
    }

    private PreparedStatement insertProfileStatement(Profile profile, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into profile (username, password, userId) values (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, profile.getUserName());
        ps.setString(2, profile.getPassWord());
        ps.setInt(3, profile.getUser().getId());

        return ps;
    }

    @Override
    public void save(Profile profile) {

        jdbcTemplate.update(connection -> insertProfileStatement(profile, connection));

    }

    @Override
    public Optional<Profile> findByUserName(String userName) {
        List<Profile> profiles =
                jdbcTemplate.query(("select * from profile where username = ?"), new ProfileRowMapper(), userName);
        if (profiles.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(profiles.get(0));
        }
    }


    private  class ProfileRowMapper implements RowMapper<Profile> {

        @Override
        public Profile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            String userName = resultSet.getString("userName");
            String password = resultSet.getString("password");
            int userId = resultSet.getInt("userId");

            return new Profile(userName, password, jdbcUserDao.findById(userId).orElse(null));
        }
    }

}

