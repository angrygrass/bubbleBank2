package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthenticationService;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import nl.hva.miw.c27.team1.cryptobanking.service.PortfolioService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sound.sampled.Port;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@WebMvcTest(PortfolioApiController.class)
public class PortfolioApiControllerTest {
    private MockMvc mockMvc;


    @MockBean
    private PortfolioService mockPortfolioService;
    @MockBean
    private AuthenticationService mockAuthenticationService;
    @MockBean
    private AuthorisationService mockAuthorisationService;
    @MockBean
    private UserService mockUserService;

    @Autowired
    public PortfolioApiControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;

    }

    @Test
    void showPortfolioHandler() throws ParseException {
        Customer customer = new Customer(0, "de", "Burcht", "test", 111222333,
                new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"), "Straat",
                "30", "1111AA", "Hilversum",
                "Netherlands", new Profile("profile", "hash", "salt", "userEnteredPassword"),
                new BankAccount("NL02ABNA0123456789"), new Portfolio(), new ArrayList<>());
        Portfolio portfolio = new Portfolio("EUR",new HashMap<>(),new Customer());
        Mockito.when(mockPortfolioService.getPortfolio(customer)).thenReturn(portfolio);
        Portfolio expected = new Portfolio();
        Portfolio actual = portfolio;
    //    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portfolio/assets");

    }



}//class
