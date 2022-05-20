package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AssetDao {

    public int save(Asset asset);
    public Asset findByCode(String code);
    public Asset findByName(String name);
    public List <Asset> getAll();

}
