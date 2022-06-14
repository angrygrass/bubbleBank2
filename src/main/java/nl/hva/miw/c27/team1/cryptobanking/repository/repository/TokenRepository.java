package nl.hva.miw.c27.team1.cryptobanking.repository.repository;

import nl.hva.miw.c27.team1.cryptobanking.model.Token;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.TokenDao;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class TokenRepository {

    private final Logger logger = LogManager.getLogger(TokenRepository.class);
    private TokenDao tokenDao;
    private UserDao userDao;

    @Autowired
    public TokenRepository(TokenDao tokenDao, UserDao userDao) {
        this.tokenDao = tokenDao;
        this.userDao = userDao;
    }

    public void save(Token token) {
        tokenDao.save(token);
    }

    //hier verder
    public Optional<Token> findByUserId(int userId) {
        return tokenDao.findByUserId(userId);
    }



}
