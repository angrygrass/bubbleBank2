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
        return rootRepository.getAllOffers();

    }


    public Transaction acceptOffer(int userId, int offerId) throws Exception {


        MarketplaceOffer offer = rootRepository.getOfferById(offerId).orElse(null);

        double transactionCostsInEuros = rootRepository.getTransactionCosts() *
                offer.getQuantity() * offer.getPrice() / 100;

        if (offer.isSellYesOrNo()) {


            if (BankAccountBalanceValidator.checkBankAccountBalance(userId, offer.getUserId(), offer.getAssetCode(),
                    offer.getQuantity(), transactionCostsInEuros, rootRepository)) {
                rootRepository.addToPortfolio(offer.getAssetCode(), userId, offer.getQuantity());
                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) - offer.getQuantity() *
                        offer.getPrice() - transactionCostsInEuros / 2);

                return concludeOffer(transactionCostsInEuros, offer, userId);





            } else {


                throw new Exception("not valid");
            }

                /*rootRepository.saveTransaction(new Transaction(0, offer.getQuantity(), offer.getPrice(),
                        LocalDateTime.now()))*/


        }


        if (!offer.isSellYesOrNo()) {

            if (CryptoBalanceValidator.checkCryptoBalance(userId, offer.getAssetCode(),
                    offer.getQuantity(), rootRepository)) {
                rootRepository.subtractFromPortfolio(userId, offer.getAssetCode(), offer.getQuantity());
                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) + offer.getQuantity() *
                        offer.getPrice() - transactionCostsInEuros / 2);

                return concludeOffer(transactionCostsInEuros, offer, userId);





            } else {


                throw new Exception("not valid");
            }

        }



        // pay transaction costs to bank

        throw new Exception("not valid");


    }


    public Transaction concludeOffer(double transactionCostsInEuros, MarketplaceOffer offer, int userId) {


        double transactionCostsInDollar = transactionCostsInEuros * (1 / Objects.requireNonNull(rootRepository.findAssetByCode("USD").orElse(null)).
                getRateInEuros());
        double bankDollarBalance = rootRepository.getQuantityOfAssetInPortfolio("usd", 1).orElse(0.0);
        rootRepository.editPortfolio("usd", 1, bankDollarBalance + transactionCostsInDollar);

        // save transaction and delete offer

        Transaction transaction = new Transaction(1, offer.getQuantity(), offer.getPrice(), LocalDateTime.now(), transactionCostsInEuros, userId,
                offer.getUserId(), offer.getAssetCode());
        transaction.setTransactionId(rootRepository.saveTransaction(transaction));
        rootRepository.deleteMarketplaceOffer(offer.getOfferId());


        return transaction;




    }

        // TODO check if the offer can be accepted by this user. If so, do the transaction and return it.



        // TODO Also adjust TransactionService class so that it uses the appropriate price (at the moment it just
        // TODO uses the current rate when doing transactions between users
        // TODO If it can't be accepted, throw Exception




    public int makeOffer (int userId, String assetCode, double quantity, double price, boolean sellYesOrNo) throws Exception {
        if (checkOfferValidity(userId, assetCode, quantity, price, sellYesOrNo)) {
            if (!sellYesOrNo) {
                rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) - quantity *
                        price - rootRepository.getTransactionCosts() * price * quantity / 100);
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
