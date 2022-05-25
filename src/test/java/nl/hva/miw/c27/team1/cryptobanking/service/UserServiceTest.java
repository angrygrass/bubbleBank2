package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

class UserServiceTest  {

    private static RootRepository rootRepository = Mockito.mock(RootRepository.class);
    private UserService userService = new UserService(rootRepository);
    private Customer testCustomer;

    @BeforeAll
    void setUp() throws ParseException {
    testCustomer = new Customer(3,"Customer", "Van","Hallo",
                478283410, new SimpleDateFormat("%y-%M-%d").parse("1937-12-16"),
                "Acoma St","2222", "4613","Proston", "Australia",
                new Profile(), new BankAccount(),new Portfolio(), new ArrayList<>());

    }

    @AfterAll
    static void tearDown() {
        System.out.println("alle testen zijn uitgevoerd");
    }

    @Test
    void register() {
        Customer actual = (Customer) userService.register(testCustomer);

    }

    @Test
    void checkBsn() {
    }

    @Test
    void checkZipCode() {
    }

    @Test
    void checkIban() {
    }

    @Test
    void checkEmail() {
    }

    @Test
    void checkPassWord() {
    }
}