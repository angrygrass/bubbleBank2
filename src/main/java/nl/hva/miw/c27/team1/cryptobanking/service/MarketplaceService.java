package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MarketplaceService {
    private MarketplaceOffer marketplaceOffer;
    private RootRepository rootRepository;
    private TransactionService transactionService;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(MarketplaceService.class);

    public MarketplaceService(RootRepository rootRepository, TransactionService transactionService) {
        this.rootRepository = rootRepository;
        this.transactionService = transactionService;
        logger.info("new empty MarketplaceService");

    }

    public List<MarketplaceOffer> getOffers() {

        // TODO get all available offers from the database through the RootRepository, MarketPlaceDao interface
        //  and JdbcMarketplaceDao class
        return rootRepository.getAll();

    }


    public Transaction acceptOffer(int userId, int offerId) {

        MarketplaceOffer offer = rootRepository.getOfferById(offerId).orElse(null);
        if (offer.isSellYesOrNo()) {
           if (transactionService) {

                /*rootRepository.saveTransaction(new Transaction(0, offer.getQuantity(), offer.getPrice(),
                        LocalDateTime.now()))*/


            }
        }

        // TODO check if the offer can be accepted by this user. If so, do the transaction and return it.


        // TODO Also adjust TransactionService class so that it uses the appropriate price (at the moment it just
        // TODO uses the current rate when doing transactions between users
        // TODO If it can't be accepted, throw Exception


        return null;

    }

    public int makeOffer (int userId, String assetCode, double quantity, double price, boolean sellYesOrNo) throws Exception {
        if (checkOfferValidity(userId, assetCode, quantity, price, sellYesOrNo)) {
            if (!sellYesOrNo) {
                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) - quantity *
                        price);
            } else {
                rootRepository.subtractFromPortfolio(userId, assetCode, quantity);

            }


            return rootRepository.saveMarketplaceOffer(new MarketplaceOffer(0, userId, LocalDateTime.now(), assetCode, quantity, price, sellYesOrNo));



        } else {
            throw new Exception("Geen geldige aanvraag");
        }
    }

    //Check methods if offer is legal
    public boolean checkOfferValidity (int userId, String assetCode, double quantity, double price, boolean sellYesOrNo) throws Exception  {


        if (marketplaceOffer.getPrice() <= 0){
            throw new Exception("Dit kan niet.");
        }
//        if () {
//
//        }
        if (!sellYesOrNo) {
            if (price * quantity > rootRepository.getBalanceByUserId(userId)) {
                return false;
            } else {
                return true;
            }

        }

        if (sellYesOrNo) {
            if (!rootRepository.checkAssetInPortfolio(assetCode, userId)) {
                return false;
            }

            double cryptoBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, userId).orElse(0.0);
            if (cryptoBalance >= quantity) {
                return true;
            }
        }
        return true;

    }

}
