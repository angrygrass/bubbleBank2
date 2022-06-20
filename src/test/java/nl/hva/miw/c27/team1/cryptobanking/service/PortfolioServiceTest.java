package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.MarketplaceDao;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.PortfolioDao;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PortfolioServiceTest {

    private PortfolioDao portfolioDao =  Mockito.mock( PortfolioDao.class );
    private MarketplaceDao marketplaceDao;

    private  RootRepository rootRepository = new RootRepository(
            null, null, null, null,null,null,portfolioDao,
            null, null, null, null, null);
    private PortfolioService portfolioService = new PortfolioService(rootRepository);



    private Customer testCustomer;
    private Asset testAsset;
    private HashMap<Asset, Double> testMap;
    private Portfolio expectedPortfolio;

    @BeforeEach
    void setUp() throws ParseException {
        testAsset = new Asset("BTC", "Bitcoin",2000.0);
        testMap = new HashMap<>();
        testMap.put(testAsset,4.0);
        //todo repair broken test due to change in Profile class!
        testCustomer = new Customer(3,"Customer", "Van","Hallo",
                111222333 ,
                new SimpleDateFormat("yyyy-MM-dd").parse("1937-12-16"),
                "Acoma St","2222", "2316LS","Proston", "Australia",
                new Profile("test@gmail.com","hash", "salt", "Paswoord12?"),
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
        Mockito.when( portfolioDao.getPortfolio( testCustomer )).thenReturn( expectedPortfolio );

        Portfolio actual = portfolioService.getPortfolio(testCustomer);
        assertEquals(testCustomer.getPortfolio(),actual );

    }
}
