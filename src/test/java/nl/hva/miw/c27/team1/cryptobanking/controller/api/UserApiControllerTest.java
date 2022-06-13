//THIS TEST IS NOT YET COMPLETE.


package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Autowired
    public UserApiControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
    }

    @Test //todo repair broken test due to change in Profile class!
    void register() throws Exception {
        Customer customer = new Customer(0, "de", "Burcht", "test", 111222333,
                new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"), "Straat",
                "30", "1111AA", "Hilversum",
                "Netherlands", new Profile("profile", "hash", "salt", "userEnteredPassword"),
                new BankAccount("NL02ABNA0123456789"), new Portfolio(), new ArrayList<>());
        Mockito.when(mockUserService.register(customer)).thenReturn(customer);
        // maak een request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users/register");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content("{\"firstName\":\"Jaak\"," +
                        "\"post\": \"de\"}");
//        JSON not yet complete
        // stuur die naar de server en wacht op response
        try {
            ResultActions response = mockMvc.perform(request);
            // check of de response aan de verwachtingen voldoet
            MvcResult result = response
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn();
//            assertEquals(result.getResponse().getStatus(), 201);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
