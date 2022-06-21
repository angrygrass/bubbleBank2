package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.MarketplaceDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientBuyerBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientSellerCryptoBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidMakeOfferRequestException;
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


    public Transaction acceptOffer(int userId, int offerId) {

        MarketplaceOffer offer = rootRepository.getOfferById(offerId).orElse(null);

        assert offer != null;
        if (offer.isSellYesOrNo()) {
            return acceptSellOffer(userId, offer);
        } else {
            return acceptBuyOffer(userId, offer);
        }
    }

    public Transaction acceptSellOffer(int userId, MarketplaceOffer offer) {
        if (BankAccountBalanceValidator.checkBankAccountBalance(userId, offer.getUserId(), offer.getAssetCode(),
                offer.getQuantity(), offer.getTransactionPrice(), rootRepository)) {
            rootRepository.addToPortfolio(offer.getAssetCode(), userId, offer.getQuantity());
            rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) - offer.getQuantity() *
                    offer.getPrice() - offer.getTransactionPrice() / 2);
            rootRepository.updateBalanceByUserId(offer.getUserId(), rootRepository.getBalanceByUserId(offer.getUserId())
                    +offer.getQuantity() * offer.getPrice() - offer.getTransactionPrice() / 2);

            return concludeOffer(offer.getTransactionPrice(), offer, userId);

        } else {
            throw new InsufficientBuyerBalanceException();
        }

    }

    public Transaction acceptBuyOffer(int userId, MarketplaceOffer offer) {
        if (CryptoBalanceValidator.checkCryptoBalance(userId, offer.getAssetCode(),
                offer.getQuantity(), rootRepository)) {
            rootRepository.subtractFromPortfolio(userId, offer.getAssetCode(), offer.getQuantity());
            rootRepository.updateBalanceByUserId(userId, rootRepository.getBalanceByUserId(userId) + offer.getQuantity() *
                    offer.getPrice() - offer.getTransactionPrice() / 2);
            rootRepository.addToPortfolio(offer.getAssetCode(), offer.getUserId(), offer.getQuantity());

            return concludeOffer(offer.getTransactionPrice(), offer, userId);

        } else {
            throw new InsufficientSellerCryptoBalanceException();
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





    public MarketplaceOffer makeOffer (MarketplaceDto marketplaceDto) {
        if (checkOfferValidity(marketplaceDto)) {
            double transactionPrice = rootRepository.getTransactionCosts() * marketplaceDto.getPrice() * marketplaceDto.getQuantity() / 100;
            if (!marketplaceDto.isSellYesOrNo()) {

                rootRepository.updateBalanceByUserId(marketplaceDto.getUserId(), rootRepository.getBalanceByUserId(marketplaceDto.getUserId())
                        - marketplaceDto.getQuantity() * marketplaceDto.getPrice() - transactionPrice / 2);
            } else {
                rootRepository.subtractFromPortfolio(marketplaceDto.getUserId(), marketplaceDto.getAssetCode(), marketplaceDto.getQuantity());

            }
            return rootRepository.saveMarketplaceOffer(new MarketplaceOffer(marketplaceDto, transactionPrice));

        } else {
            throw new InvalidMakeOfferRequestException();
        }
    }

    public void cancelOffer(int offerId) {
        MarketplaceOffer offer = rootRepository.getOfferById(offerId).orElse(null);
        assert offer != null;
        if (!offer.isSellYesOrNo()) {
            rootRepository.updateBalanceByUserId(offer.getUserId(), rootRepository.getBalanceByUserId(offer.getUserId())
                    + offer.getQuantity() * offer.getPrice() + offer.getTransactionPrice() / 2);
        } else {
            rootRepository.addToPortfolio(offer.getAssetCode(), offer.getUserId(), offer.getQuantity());

        }
        rootRepository.deleteMarketplaceOffer(offerId);
    }

    //Check methods if offer is legal
    public boolean checkOfferValidity (MarketplaceDto marketplaceDto) {

        if (marketplaceDto.getPrice() <= 0){
            throw new InvalidMakeOfferRequestException();
        }//
        if (!marketplaceDto.isSellYesOrNo()) {
            return checkBuyOfferValidity(marketplaceDto);
        } else {
            return checkSellOfferValidity(marketplaceDto);
        }
    }


    public boolean checkSellOfferValidity (MarketplaceDto marketplaceDto) {
        if (!rootRepository.checkAssetInPortfolio(marketplaceDto.getAssetCode(), marketplaceDto.getUserId())) {
            return false;
        }

        double cryptoBalance = rootRepository.getQuantityOfAssetInPortfolio(marketplaceDto.getAssetCode(),
                marketplaceDto.getUserId()).orElse(0.0);
        return cryptoBalance >= marketplaceDto.getQuantity();
    }

    public boolean checkBuyOfferValidity (MarketplaceDto marketplaceDto) {

        double transactionCostsInEuros = rootRepository.getTransactionCosts() *
                marketplaceDto.getQuantity() * marketplaceDto.getPrice() / 100;
        return !(marketplaceDto.getPrice() * marketplaceDto.getQuantity() + transactionCostsInEuros >
                rootRepository.getBalanceByUserId(marketplaceDto.getUserId()));
    }
}
