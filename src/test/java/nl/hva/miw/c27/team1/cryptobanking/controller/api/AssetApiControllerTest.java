package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import com.google.gson.Gson;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.service.AssetService;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.catalina.connector.Response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    public AssetApiControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;

    }

    @Test
    void getAllAssetRates() {
        // Zet eerst de testomgeving op, te beginnen met het vullen van de mock objecten:
        // De RestApiController is afhankelijk van de RomanNumeralService waarvoor we een
        // mockobject hebben gemaakt
        List<Asset> assetList = new ArrayList<>();

        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        assetList.add(asset);
        Mockito.when(assetService.getRates()).thenReturn(assetList);
        Gson gson = new Gson();
        String assetJson = gson.toJson(asset);
        // Maak dan een request object

        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/");

        asset_request.header("authorization", "authorization");


        // En nu kunnen we de testomgeving gebruiken:

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

        // Zet eerst de testomgeving op, te beginnen met het vullen van de mock objecten:
        // De RestApiController is afhankelijk van de RomanNumeralService waarvoor we een
        // mockobject hebben gemaakt

        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        Mockito.when(assetService.getRate("bitcoin")).thenReturn(Optional.of(asset));
        Gson gson = new Gson();
        String assetJson = gson.toJson(asset);
        // Maak dan een request object

        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/" + asset.getAssetName());

        asset_request.header("authorization", "authorization");


        // En nu kunnen we de testomgeving gebruiken:

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
        List<AssetHistoryDto> assethistoryList = new ArrayList<>();
        assethistoryList.add(new AssetHistoryDto(null, asset.getAssetCode(), asset.getRateInEuros()));
        Mockito.when(assetService.getHistoricRates(asset.getAssetCode(), 3)).thenReturn(Optional.of(assethistoryList));
        Gson gson = new Gson();
        String assetJson = gson.toJson(asset);
        // Maak dan een request object

        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/history/" + asset.getAssetCode() + "/?chartdays=" + 3);

        asset_request.header("authorization", "authorization");


        // En nu kunnen we de testomgeving gebruiken:

        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk()); // Dit hadden we al bij de demo.
            String responseBody = response.andReturn().getResponse().getContentAsString(); // dit is nieuw!
            assertThat(responseBody).isEqualTo("[{\"dateTime\":null,\"symbol\":\""+asset.getAssetCode() + "\",\"current_price\":" + asset.getRateInEuros() + "}]"); // En nu kunnen we "gewoon" AssertJ gebruiken.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

//"[{\"dateTime\":null,\"symbol\":null,\"current_price\":0.0}]"