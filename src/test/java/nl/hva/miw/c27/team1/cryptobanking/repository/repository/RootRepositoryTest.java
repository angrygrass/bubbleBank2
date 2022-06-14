package nl.hva.miw.c27.team1.cryptobanking.repository.repository;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class RootRepositoryTest {


    private final Logger logger = LoggerFactory.getLogger(RootRepositoryTest.class);

    private RootRepository rootRepository;

    @Autowired
    public RootRepositoryTest(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New instance of RootRepositoryTest created.");
    }

    @Test
    public void rootRepository_is_available() {
        assertThat(rootRepository).isNotNull();
    }

    @Test
    void findAssetByName() {
        Asset result = rootRepository.findAssetByName("bitcoin").orElse(null);


        assert result != null;
        assertThat(result.getAssetCode()).isEqualTo("btc");
    }

    @Test
    void getAllAssets() {
        Asset result = rootRepository.getAllAssets().get(0);
        assertThat(result.getAssetCode()).isEqualTo("ada");
    }

    @Test
    void getAllHistoricAssetsDatabase() {
        String result = Objects.requireNonNull(rootRepository.getAllHistoricAssetsDatabase("btc", 7).orElse(null)).
                get(0).getAssetCode();
        assertThat(result).isEqualTo("btc");
    }
}