package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssetHistoryDao {

    void saveAssetHistoryList(List<AssetHistoryDto> assetHistoryList);
    Optional<List> getAllHistoricAssets(String assetName, int numberDays);

}
