package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface AssetDao {

    void save(Asset asset);
    void saveAllAssets(List<Asset> assetList);
    Optional<Asset> findByCode(String code);
    Optional<Asset> findByName(String name);
    List<Asset> getAll();

}
