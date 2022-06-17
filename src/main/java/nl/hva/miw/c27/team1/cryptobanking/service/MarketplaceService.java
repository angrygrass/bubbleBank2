package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketplaceService {

    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(MarketplaceService.class);

    public MarketplaceService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("new empty MarketplaceService");

    }

    public List<MarketplaceOffer> getOffers() {

        // TODO get all available offers from the database through the RootRepository, MarketPlaceDao interface
        //  and JdbcMarketplaceDao class

        return null;
    }


    public Transaction acceptOffer(int userId, int offerId) {

        // TODO check if the offer can be accepted by this user. If buy offer,
        // TODO the user should have enough assets, if sell offer, the user should have enough money in bank account.
        // TODO If so, do the transaction and return it.
        // TODO Also adjust TransactionService class so that it uses the appropriate price (at the moment it just
        // TODO uses the current rate when doing transactions between users
        // TODO If it can't be accepted, throw Exception


        return null;

    }
}
