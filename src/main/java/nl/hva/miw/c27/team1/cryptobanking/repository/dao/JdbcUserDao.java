package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(Transaction.class);

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcUserDao.");
    }

    private PreparedStatement insertUserStatement(User user, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into user (firstname, prefix, surname, dateofbirth, fiscalnumber, streetname," +
                        "housenumber, zipcode, residence, country, role) values (?, ?, ?, ?, ?, ?, ?," +
                        "?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getPrefix());
        ps.setString(3, user.getSurName());
        ps.setDate(4, (Date) user.getBirthDate());
        ps.setInt(5, user.getBsnNumber());
        ps.setString(6, user.getStreetName());
        ps.setString(7, user.getHouseNumber());
        ps.setString(8, user.getZipCode());
        ps.setString(9, user.getResidence());
        ps.setString(10, user.getCountry());
        ps.setString(11, user.getRole());


        return ps;
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertUserStatement(user, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        user.setId(newKey);
    }


    @Override
    public Optional<User> findById(int id) {
        List<User> users =
                jdbcTemplate.query("select * from user where userId = ?", new UserRowMapper(), id);
        if (users.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(0));
        }
    }


    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int id = resultSet.getInt("userid");
            String firstname = resultSet.getString("full_name");
            String prefix = resultSet.getString("username");
            String surname = resultSet.getString("password");
            Date date = resultSet.getDate("dateOfBirth");
            int fiscalnumber = resultSet.getInt("fiscalnumber");
            String streetname = resultSet.getString("streetname");
            String housenumber = resultSet.getString("housenumber");
            String zipcode = resultSet.getString("zipcode");
            String residence = resultSet.getString("residence");
            String country = resultSet.getString("country");
            String role = resultSet.getString("role");
            int staffId = resultSet.getInt("staffId");
            User user = new Customer(id, role);
            if (role.equals("Customer")) {
                user = new Customer(firstname, prefix, surname, fiscalnumber, date, streetname, housenumber, zipcode,
                        residence, country, new Profile());
            }
            if (role.equals("Admin")) {
                user = new Admin(firstname, prefix, surname, fiscalnumber, date, streetname, housenumber, zipcode,
                        residence, country, new Profile(), staffId);
            }
            user.setId(id);
            return user;
        }
    }
}
