package nl.hva.miw.c27.team1.cryptobanking;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.JdbcUserDao;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Optional;
import java.util.logging.Logger;

@SpringBootApplication
public class BubbleBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BubbleBankApplication.class, args);
    }




}
