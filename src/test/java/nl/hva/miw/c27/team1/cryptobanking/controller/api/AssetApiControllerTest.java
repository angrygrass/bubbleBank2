package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.google.gson.Gson;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.service.AssetService;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.catalina.connector.Response;

import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssetApiController.class)

class AssetApiControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private AssetService assetService;

    @MockBean
    AuthorisationService authorisationService;

    @Autowired
    public AssetApiControllerTest(MockMvc mockMvc, AssetService assetService, AuthorisationService authorisationService) {
        this.assetService = assetService;
        this.mockMvc = mockMvc;
        this.authorisationService = authorisationService;

    }



    @Test
    void getAllAssetRates() {

        List<Asset> assetList = new ArrayList<>();

        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        assetList.add(asset);
        Mockito.when(assetService.getRates()).thenReturn(assetList);


        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/");

        asset_request.header(HttpHeaders.AUTHORIZATION, "Bearer 36716df7-839b-426e-8bc0-ac3ad5580004");



        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk()); // Dit hadden we al bij de demo.
            String responseBody = response.andReturn().getResponse().getContentAsString(); // dit is nieuw!
            assertThat(responseBody).isEqualTo("[{\"id\":\"" + asset.getAssetName() +
                    "\",\"symbol\":\"" + asset.getAssetCode() + "\",\"current_price\":" + asset.getRateInEuros() +"}]"); // En nu kunnen we "gewoon" AssertJ gebruiken.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAssetRate() throws IOException {


        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        Mockito.when(assetService.getRate("bitcoin")).thenReturn(Optional.of(asset));

        MockHttpServletRequestBuilder asset_request =
        MockMvcRequestBuilders.get("/assets/" + asset.getAssetName());

        asset_request.header("authorization", "authorization");


        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk()); // Dit hadden we al bij de demo.
            String responseBody = response.andReturn().getResponse().getContentAsString(); // dit is nieuw!


            assertThat(responseBody).isEqualTo("{\"id\":\"" + asset.getAssetName() +
                   "\",\"symbol\":\"" + asset.getAssetCode() + "\",\"current_price\":" + asset.getRateInEuros() +"}"); // En nu kunnen we "gewoon" AssertJ gebruiken.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void getHistoricAssetRate() {
        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        Asset asset2 = new Asset("ape", "apecoin", 5.00);

        List<AssetHistoryDto> assethistoryList = new ArrayList<>();
        assethistoryList.add(new AssetHistoryDto(null, asset.getAssetCode(), asset.getRateInEuros()));
        assethistoryList.add(new AssetHistoryDto(null, asset2.getAssetCode(), asset2.getRateInEuros()));

        Mockito.when(assetService.getHistoricRates(asset.getAssetCode(), 3)).thenReturn(Optional.of(assethistoryList));


        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/history/" + asset.getAssetCode() + "/?chartdays=" + 3);

        asset_request.header("authorization", "authorization");


        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk()); // Dit hadden we al bij de demo.
            String responseBody = response.andReturn().getResponse().getContentAsString(); // dit is nieuw!
            assertThat(responseBody).isEqualTo("[{\"dateTime\":null,\"symbol\":\""+asset.getAssetCode() +
                    "\",\"current_price\":" + asset.getRateInEuros() + "}," + "{\"dateTime\":null,\"symbol\":\""+asset2.getAssetCode() +
                    "\",\"current_price\":" + asset2.getRateInEuros() + "}]"); // En nu kunnen we "gewoon" AssertJ gebruiken.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

