package nl.hva.miw.c27.team1.cryptobanking.repository.dao;


import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
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
public class JdbcTokenDao implements TokenDao {

        private JdbcTemplate jdbcTemplate;
        private final Logger logger = LogManager.getLogger(JdbcTokenDao.class);



        @Autowired
        public JdbcTokenDao(JdbcTemplate jdbcTemplate) {
            super();
            this.jdbcTemplate = jdbcTemplate;

            logger.info("New JdbcTokenDao.");
        }

        private PreparedStatement insertTokenStatement(Token token, Connection connection) throws SQLException {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into token(idToken, `valid until`, userId) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, token.getTokenId());
            ps.setTimestamp(2, token.getValiduntil());
            ps.setInt(3, token.getUser().getId());

            return ps;
        }

        @Override
        public void save(Token token) {

            jdbcTemplate.update(connection -> insertTokenStatement(token, connection));

        }

        @Override
        public void revokeToken(User user) {
            jdbcTemplate.update(("delete from token where userId = ?;"), user.getId());

        }


        @Override
        public Optional<Token> findByUserId(int userId) {
            List<Token> tokens =
                    jdbcTemplate.query(("select * from token where userId = ?" +
                            " order by `valid until` desc limit 1;"), new TokenRowMapper(), userId);
            if (tokens.size() != 1) {
                return Optional.empty();
            } else {
                return Optional.of(tokens.get(0));
            }
        }


        private static class TokenRowMapper implements RowMapper<Token> {

            @Override
            public Token mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                String idToken = resultSet.getString("idToken");
                Timestamp validuntil = resultSet.getTimestamp("valid until");
                int userId = resultSet.getInt("userId");

                JdbcUserDao jdbcUserDao = new JdbcUserDao(new JdbcTemplate());
                return new Token(idToken, validuntil, jdbcUserDao.findById(userId).orElse(null));
            }
        }

    }




