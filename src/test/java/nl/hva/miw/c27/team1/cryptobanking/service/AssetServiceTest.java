package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AssetServiceTest {


    RootRepository mockRootRepository = Mockito.mock(RootRepository.class);
    AssetService assetService = new AssetService(mockRootRepository);

    Asset asset = new Asset("btc", "bitcoin", 30000.00);
    Asset asset2 = new Asset("ape", "apecoin", 5.00);



    public AssetServiceTest() {
        super();
    }


    @BeforeEach
    public void testSetup() {
        List<AssetHistoryDto> assetHistoryDtoList = new ArrayList<>();
        assetHistoryDtoList.add(0, new AssetHistoryDto(LocalDate.now(), "btc", 30000.00));

        Mockito.when(mockRootRepository.getAllHistoricAssetsDatabase("btc", 3)).
                thenReturn(Optional.of(assetHistoryDtoList));


        Mockito.when(mockRootRepository.findAssetByName("bitcoin")).thenReturn(Optional.of(asset));

        List<Asset> assetList = new ArrayList<>();
        assetList.add(asset);
        assetList.add(asset2);

        Mockito.when(mockRootRepository.getAllAssets()).thenReturn(assetList);
    }



    @Test
    void getRates() {
        List<Asset> actual = assetService.getRates();
        List<Asset> expected = new ArrayList<>();
        expected.add(asset);
        expected.add(asset2);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getRate() {
        String actual = Objects.requireNonNull(assetService.getRate("bitcoin").orElse(null)).getAssetName();
        String expected = "bitcoin";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getHistoricRates() {
        double actual = Objects.requireNonNull(assetService.getHistoricRates("btc", 3).orElse(null)).
                get(0).getRate();
        double expected = 30000.00;
        assertThat(actual).isEqualTo(expected);


    }
}