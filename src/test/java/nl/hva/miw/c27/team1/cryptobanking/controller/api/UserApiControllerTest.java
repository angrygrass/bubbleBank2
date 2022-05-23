/*
package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import net.bytebuddy.asm.Advice;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RegisterDto;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService mockService;

    @Autowired
    public UserApiControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
    }

    @Test
    public void register_test() throws Exception {
        Customer customer = new Customer(0,"Ben", "de", "boer",4948204,
                new Date(),"straat","2314","23",
                "Amsterdam","Nederland", new Profile());

        Mockito.when(mockService.register(customer)).thenReturn(customer);
        // maak een request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/register");
        // stuur die naar de server en wacht op response
        try {
            ResultActions response = mockMvc.perform(request);
            // check of de response aan de verwachtingen voldoet
            MvcResult result = response
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            Assertions.assertThat(result.getResponse().getContentType()).contains("json");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
*/
