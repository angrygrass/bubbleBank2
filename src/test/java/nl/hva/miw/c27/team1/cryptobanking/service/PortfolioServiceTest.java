package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PortfolioServiceTest {

    @MockBean
    private static RootRepository rootRepository = Mockito.mock(RootRepository.class);
    @MockBean
    private PortfolioService portfolioService = new PortfolioService(rootRepository);

    private Customer testCustomer;
    private Asset testAsset;
    private Map<Asset, Double> testMap;
    private Portfolio expectedPortfolio;

    @BeforeEach
    void setUp() throws ParseException {
        testAsset = new Asset("BTC", "Bitcoin",2000.0);
        testMap = new HashMap<>();
        testMap.put(testAsset,4.0);

        testCustomer = new Customer(3,"Customer", "Van","Hallo",
                111222333 ,
                new SimpleDateFormat("yyyy-MM-dd").parse("1937-12-16"),
                "Acoma St","2222", "2316LS","Proston", "Australia",
                new Profile("test@gmail.com","Paswoord12?"),
                new BankAccount("NL02ABNA0123456789",5000),
                new Portfolio(testMap, testCustomer), new ArrayList<>());
        expectedPortfolio = new Portfolio(testMap,testCustomer);
    }

    @AfterEach
    void tearDown() {
        System.out.println("alle testen zijn uitgevoerd");
    }

    @Test
    void getPortfolio() {
        Portfolio actual = portfolioService.getPortfolio(testCustomer);
        assertEquals(actual, testCustomer.getPortfolio());

    }
}
