package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.service.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(LoginApiController.class)

class LoginApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    void loginUser() {
        Mockito
                .when(loginService.login("test_user", "123Test"))
                .thenReturn("token");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/login");
        request.param("username", "test_user");
        request.param("password", "123Test");

        try {
            ResultActions response = mockMvc.perform(request);
            response.andExpect(MockMvcResultMatchers.status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public LoginApiControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
}