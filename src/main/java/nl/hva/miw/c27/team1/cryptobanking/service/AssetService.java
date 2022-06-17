package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(AssetService.class);

    public AssetService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("new AssetService");
    }

    public List<Asset> getRates() {
        return rootRepository.getAllAssets();
    }

    public Optional<Asset> getRate(String assetName) {
        return rootRepository.findAssetByName(assetName);
    }

    public Optional<List<AssetHistoryDto>> getHistoricRates(String assetCode, int numberDays) {
        return rootRepository.getAllHistoricAssetsDatabase(assetCode, numberDays);
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }

    public String getInvalidAssetMsg() {return rootRepository.getInvalidAssetMsg();}
}
