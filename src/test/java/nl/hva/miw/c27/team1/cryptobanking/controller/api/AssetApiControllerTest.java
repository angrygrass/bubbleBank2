package nl.hva.miw.c27.team1.cryptobanking.controller.api;




import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.service.AssetService;
import nl.hva.miw.c27.team1.cryptobanking.service.AuthorisationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(AssetApiController.class)
class AssetApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssetService assetService;

    @MockBean
    private AuthorisationService authorisationService;





    @Test
    void getAllAssetRates() {

        List<Asset> assetList = new ArrayList<>();

        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        assetList.add(asset);
        Mockito.when(assetService.getRates()).thenReturn(assetList);
        Mockito.when(authorisationService.checkCustomerAuthorisation("authorization")).thenReturn(true);

        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/");

        asset_request.header(HttpHeaders.AUTHORIZATION, "authorization");



        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk());
            String responseBody = response.andReturn().getResponse().getContentAsString();
            assertThat(responseBody).isEqualTo("[{\"id\":\"" + asset.getAssetName() +
                    "\",\"symbol\":\"" + asset.getAssetCode() + "\",\"current_price\":" + asset.getRateInEuros() +"}]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAssetRate() throws IOException {


        Asset asset = new Asset("btc", "bitcoin", 30000.00);
        Mockito.when(assetService.getRate("bitcoin")).thenReturn(Optional.of(asset));
        Mockito.when(authorisationService.checkCustomerAuthorisation("authorization")).thenReturn(true);

        MockHttpServletRequestBuilder asset_request =
        MockMvcRequestBuilders.get("/assets/" + asset.getAssetName());

        asset_request.header("authorization", "authorization");


        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk());
            String responseBody = response.andReturn().getResponse().getContentAsString();


            assertThat(responseBody).isEqualTo("{\"id\":\"" + asset.getAssetName() +
                   "\",\"symbol\":\"" + asset.getAssetCode() + "\",\"current_price\":" + asset.getRateInEuros() +"}");
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
        Mockito.when(authorisationService.checkCustomerAuthorisation("authorization")).thenReturn(true);


        MockHttpServletRequestBuilder asset_request =
                MockMvcRequestBuilders.get("/assets/history/" + asset.getAssetCode() + "/?chartdays=" + 3);

        asset_request.header("authorization", "authorization");


        try {
            ResultActions response = mockMvc.perform(asset_request);
            response.andDo(print()).andExpect(status().isOk());
            String responseBody = response.andReturn().getResponse().getContentAsString();
            assertThat(responseBody).isEqualTo("[{\"dateTime\":null,\"symbol\":\""+asset.getAssetCode() +
                    "\",\"current_price\":" + asset.getRateInEuros() + "}," + "{\"dateTime\":null,\"symbol\":\""+asset2.getAssetCode() +
                    "\",\"current_price\":" + asset2.getRateInEuros() + "}]");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

