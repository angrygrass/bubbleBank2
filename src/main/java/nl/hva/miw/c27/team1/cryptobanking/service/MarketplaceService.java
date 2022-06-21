package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.BankAccountBalanceValidator;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.CryptoBalanceValidator;
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


    @JsonIgnore
    private final Logger logger = LogManager.getLogger(MarketplaceService.class);

    public MarketplaceService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;

        logger.info("new empty MarketplaceService");

    }

    public List<MarketplaceOffer> getOffers() {

        // TODO get all available offers from the database through the RootRepository, MarketPlaceDao interface
        //  and JdbcMarketplaceDao class
        return rootRepository.getAllOffers();

    }

    // userId is user who has made the offer, price is price for 1 unit of the asset, sellYesOrNo true = sell, false = buy


    public Transaction acceptOffer(int userId, int offerId) throws Exception {


        MarketplaceOffer offer = rootRepository.getOfferById(offerId).orElse(null);

                if (offer.isSellYesOrNo()) {


            if (BankAccountBalanceValidator.checkBankAccountBalance(userId, offer.getUserId(), offer.getAssetCode(),
                    offer.getQuantity(), offer.getTransactionPrice(), rootRepository)) {
                rootRepository.addToPortfolio(offer.getAssetCode(), userId, offer.getQuantity());
                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) - offer.getQuantity() *
                        offer.getPrice() - offer.getTransactionPrice() / 2);
                rootRepository.updateBalanceByUserId(offer.getUserId(), rootRepository.getBalanceByUserId(offer.getUserId())
                +offer.getQuantity() * offer.getPrice() - offer.getTransactionPrice() / 2);

                return concludeOffer(offer.getTransactionPrice(), offer, userId);

            } else {
                throw new Exception("not valid");
            }

                       }

        else {

            if (CryptoBalanceValidator.checkCryptoBalance(userId, offer.getAssetCode(),
                    offer.getQuantity(), rootRepository)) {
                rootRepository.subtractFromPortfolio(userId, offer.getAssetCode(), offer.getQuantity());
                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) + offer.getQuantity() *
                        offer.getPrice() - offer.getTransactionPrice() / 2);
                rootRepository.addToPortfolio(offer.getAssetCode(), offer.getUserId(), offer.getQuantity());

                return concludeOffer(offer.getTransactionPrice(), offer, userId);

            } else {
                throw new Exception("not valid");
            }

        }


    }


    public Transaction concludeOffer(double transactionCostsInEuros, MarketplaceOffer offer, int userId) {

        double transactionCostsInDollar = transactionCostsInEuros * (1 / Objects.requireNonNull(rootRepository.findAssetByCode("USD").orElse(null)).
                getRateInEuros());
        double bankDollarBalance = rootRepository.getQuantityOfAssetInPortfolio("usd", 1).orElse(0.0);
        rootRepository.editPortfolio("usd", 1, bankDollarBalance + transactionCostsInDollar);

        // save transaction and delete offer


        Transaction transaction = new Transaction(1, offer.getQuantity(), offer.getPrice(), LocalDateTime.now(), transactionCostsInEuros, userId,
                offer.getUserId(), offer.getAssetCode());

        if (!offer.isSellYesOrNo()) {

            transaction.setBuyerId(offer.getUserId());
            transaction.setSellerId(userId);
        }



        transaction.setTransactionId(rootRepository.saveTransaction(transaction));
        rootRepository.deleteMarketplaceOffer(offer.getOfferId());


        return transaction;

    }





    public MarketplaceOffer makeOffer (int userId, String assetCode, double quantity, double price, boolean sellYesOrNo) throws Exception {
        if (checkOfferValidity(userId, assetCode, quantity, price, sellYesOrNo)) {
            double transactionPrice = rootRepository.getTransactionCosts() * price * quantity / 100;
            if (!sellYesOrNo) {

                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) - quantity *
                        price - transactionPrice / 2);
            } else {
                rootRepository.subtractFromPortfolio(userId, assetCode, quantity);

            }
            return rootRepository.saveMarketplaceOffer(new MarketplaceOffer(0, userId, LocalDateTime.now(), assetCode, quantity, price, sellYesOrNo, transactionPrice));

        } else {
            throw new Exception("Geen geldige aanvraag");
        }
    }

    public void cancelOffer(int offerId) {
        MarketplaceOffer offer = rootRepository.getOfferById(offerId).orElse(null);
        if (!offer.isSellYesOrNo()) {
            rootRepository.updateBalanceByUserId(offer.getUserId(), rootRepository.getBalanceByUserId(offer.getUserId())
                    + offer.getQuantity() * offer.getPrice() + offer.getTransactionPrice() / 2);
        } else {
            rootRepository.addToPortfolio(offer.getAssetCode(), offer.getUserId(), offer.getQuantity());

        }


        rootRepository.deleteMarketplaceOffer(offerId);


    }

    //Check methods if offer is legal
    public boolean checkOfferValidity (int userId, String assetCode, double quantity, double price, boolean sellYesOrNo) throws Exception  {


        if (price <= 0){
            throw new Exception("Dit kan niet.");
        }
//        if () {
//
//        }
        if (!sellYesOrNo) {
            double transactionCostsInEuros = rootRepository.getTransactionCosts() *
                    quantity * price / 100;
            if (price * quantity + transactionCostsInEuros > rootRepository.getBalanceByUserId(userId)) {
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
