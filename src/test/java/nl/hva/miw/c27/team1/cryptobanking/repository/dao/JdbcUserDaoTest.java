package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class JdbcUserDaoTest {

    private UserDao daoUnderTest;



    @Autowired
    public JdbcUserDaoTest(UserDao userDao) {
        super();
        this.daoUnderTest = userDao;

    }
    @Test
    void save() {
    }

    /*@Test
    void findById() {
        Optional<User> actual = daoUnderTest.findById(1);
        Optional<User> expected = Optional.of(new Customer(1, "Customer"));
        assertThat(actual).isEqualTo(expected);
    }*/

    @Test
    void getAllUsers() {
    }
}