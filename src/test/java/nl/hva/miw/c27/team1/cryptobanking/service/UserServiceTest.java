package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit-test
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest  {

    @MockBean
    private static RootRepository rootRepository = Mockito.mock(RootRepository.class);
    @MockBean
    private UserService userService = new UserService(rootRepository);
    private Customer testCustomer;

    @BeforeAll
    void setUp() throws ParseException {
        testCustomer = new Customer(3,"Customer", "Van","Hallo",
                111222333 , new SimpleDateFormat("yyyy-MM-dd").parse("1937-12-16"),
                "Acoma St","2222", "2316LS","Proston", "Australia",
                new Profile("test@gmail.com","Paswoord12?"),
                new BankAccount("NL02ABNA0123456789",5000),new Portfolio(), new ArrayList<>());
    }

    @AfterAll
    static void tearDown() {
        System.out.println("alle testen zijn uitgevoerd");
    }

    @Test
    void register() {
        User actual = userService.register(testCustomer);
        assertEquals(actual, testCustomer);
    }

}
