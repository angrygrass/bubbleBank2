package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.Optional;

public interface MarketplaceDao {
    List<MarketplaceOffer> getAllOffers();

    Optional<MarketplaceOffer> getOfferById(int id);

    MarketplaceOffer save(MarketplaceOffer marketplaceOffer);

    void deleteOfferById(int id);

    Optional<MarketplaceOffer> editMarketplaceOffer(MarketplaceOffer marketplaceOffer);
}
