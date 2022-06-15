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
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(JdbcUserDao.class);

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
        java.util.Date utilDate = user.getBirthDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        ps.setDate(4, sqlDate);
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
        int newKey = Objects.requireNonNull(keyHolder.getKey()).intValue();
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

    @Override
    public Optional<User> findByToken(Token token) {
        System.out.println(token.getTokenId());
        List<User> users =

                jdbcTemplate.query("select * from user where userId = (select userId from" +
                        " token where idToken = ?);", new UserRowMapper(), token.getTokenId());
        if (users.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(0));
        }
    }
    @Override
    public Optional<User> getByRole(String role) {
        List<User> users =
                jdbcTemplate.query("select * from user where role = ?", new UserRowMapper(), role);
        if (users.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(0));
        }
    }



    @Override
    public Optional<Customer> updateCustomer(Customer user) {

        String sql = "UPDATE `User` SET firstName = ?, prefix = ?, surname = ?, dateOfBirth = ?," +
                "fiscalNumber = ?, streetName = ?, houseNumber = ?, zipCode = ?, residence = ?, country = ?, " +
                "role = ? WHERE userId = ?;";
        jdbcTemplate.update(sql, user.getFirstName(), user.getPrefix(), user.getSurName(), user.getBirthDate(),
               user.getBsnNumber(), user.getStreetName(), user.getHouseNumber(), user.getZipCode(),
                user.getResidence(), user.getCountry(), user.getRole());
        return Optional.of(user);

    }
    @Override
    public void deleteUserById(int id) {
        String sql = "DELETE FROM `User` WHERE userId = ?;";
        jdbcTemplate.update(sql, id);
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users =
                jdbcTemplate.query("select * from user", new UserRowMapper());

        return users;
    }





    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int id = resultSet.getInt("userid");
            String firstname = resultSet.getString("firstname");
            String prefix = resultSet.getString("prefix");
            String surname = resultSet.getString("surname");
            Date dateOfBirth = resultSet.getDate("dateOfBirth");
            int fiscalnumber = resultSet.getInt("fiscalnumber");
            String streetname = resultSet.getString("streetname");
            String housenumber = resultSet.getString("housenumber");
            String zipcode = resultSet.getString("zipcode");
            String residence = resultSet.getString("residence");
            String country = resultSet.getString("country");
            String role = resultSet.getString("role");
            int staffId = resultSet.getInt("staffId");
            User user = null;
            if (role.equals("Customer") || role.equals("Bank")) {
                user = new Customer(id, firstname, prefix, surname, fiscalnumber, dateOfBirth, streetname, housenumber, zipcode,
                        residence, country);
            }
            if (role.equals("Admin")) {
                user = new Admin(id, firstname, surname, fiscalnumber, dateOfBirth, streetname, housenumber, zipcode,
                        residence, country, staffId);
            }

            return user;
        }
    }
}
