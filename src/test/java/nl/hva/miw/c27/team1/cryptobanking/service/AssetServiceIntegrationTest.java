package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AssetServiceIntegrationTest {

    private AssetService assetService;

    @Autowired
    public AssetServiceIntegrationTest(AssetService service) {
        super();
        assetService = service;
    }

    @Test
    public void testServiceAvailable() {
        assertThat(assetService).isNotNull();
    }

    @Test
    public void testRepoAvalable() {
        assertThat(assetService.getRootRepository()).isNotNull();
    }

    @Test
    void getRates() {
        Asset result = assetService.getRates().get(0);
        assertThat(result.getAssetCode()).isEqualTo("ada");
    }

    @Test
    void getRate() {

        String actual = Objects.requireNonNull(assetService.getRate("bitcoin").orElse(null)).getAssetCode();
        String expected = "btc";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getHistoricRates() {
        String result = Objects.requireNonNull(assetService.getHistoricRates("btc", 7).orElse(null)).
                get(0).getAssetCode();
        assertThat(result).isEqualTo("btc");


    }
}