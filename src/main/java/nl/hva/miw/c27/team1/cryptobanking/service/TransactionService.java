package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.Globals;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientSellerCryptoBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientBuyerBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InvalidAssetRequest;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.BankAccountBalanceValidator;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.CryptoBalanceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Objects;




@Service
public class TransactionService {
    private final RootRepository rootRepository;
    private final AssetService assetService;
    private final Logger logger = LogManager.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, AssetService assetService) {
        super();
        this.rootRepository = rootRepository;
        this.assetService = assetService;
        logger.info("new BuyAssetService");
    }

    public Transaction doTransaction(int buyerId, int sellerId, String assetCode, double quantity) {
        //calculate transaction costs in euro - ATTENTION: TRANSACTIONCOSTS IN THE DATABASE IS A PERCENTAGE!
        double transactionCostsInEuros = getTransactionCostsInEuro(assetCode, quantity);
        //check if customer bank account balance and bank crypto balance are sufficient for the transaction

        doBalanceChecks(buyerId, sellerId, assetCode, quantity, transactionCostsInEuros);
        //check if asset is already in buyer's portfolio - if not save the new asset + amount, else update current amount
        rootRepository.addToPortfolio(assetCode, buyerId, quantity);
        // edit the seller's portfolio (subtracting the quantity of the bought asset)
        rootRepository.subtractFromPortfolio(sellerId, assetCode, quantity);
        // edit buyer and seller's bank account balance
        rootRepository.updateBalanceByUserId(buyerId, rootRepository.getBalanceByUserId(buyerId) - quantity *
                Objects.requireNonNull(rootRepository.findAssetByCode(assetCode).orElse(null)).getRateInEuros());
        rootRepository.updateBalanceByUserId(sellerId, rootRepository.getBalanceByUserId(sellerId) + quantity *
                Objects.requireNonNull(rootRepository.findAssetByCode(assetCode).orElse(null)).getRateInEuros());
        // save transaction to database
        Transaction transaction = new Transaction(1, quantity, Objects.requireNonNull(rootRepository.findAssetByCode(assetCode).
                orElse(null)).getRateInEuros(), LocalDateTime.now(), transactionCostsInEuros, buyerId, sellerId,
                assetCode);
        transaction.setTransactionId(rootRepository.saveTransaction(transaction));
       // pay transaction costs
        payTransactionCosts(transactionCostsInEuros, buyerId, sellerId);
        return transaction;
    }

    public double getTransactionCostsInEuro(String assetCode, double quantity) {
        Asset asset = rootRepository.findAssetByCode(assetCode).orElse(null);
        if (asset != null) {
            return rootRepository.getTransactionCosts() * quantity * asset.getRateInEuros() / 100;
        } else {
            throw new InvalidAssetRequest(assetService.getInvalidAssetMsg());
        }

    }

    public void doBalanceChecks(int buyerId, int sellerId, String assetCode, double quantity,
                                 double transactionCostsInEuros) {
        if (!BankAccountBalanceValidator.checkBankAccountBalance(buyerId, sellerId, assetCode, quantity,
                transactionCostsInEuros, rootRepository)) {
            throw new InsufficientBuyerBalanceException();
        }
        if (!CryptoBalanceValidator.checkCryptoBalance(sellerId, assetCode, quantity, rootRepository)) {
            throw new InsufficientSellerCryptoBalanceException();
        }
    }

    public String getUserName(int id) {
        return Objects.requireNonNull(rootRepository.getUserById(id).orElse(null)).getFirstName() + " " +
                Objects.requireNonNull(rootRepository.getUserById(id).orElse(null)).getPrefix() + " " +
                Objects.requireNonNull(rootRepository.getUserById(id).orElse(null)).getSurName();
    }

    public String getCryptoName(String code) {
        return Objects.requireNonNull(rootRepository.findAssetByCode(code).orElse(null)).getAssetName();
    }


    private void payTransactionCosts(double transactionCosts, int buyerId, int sellerId) {

        double transactionCostsInDollar = transactionCosts * (1 / Objects.requireNonNull(rootRepository.findAssetByCode("USD").orElse(null)).
                getRateInEuros());
        double bankDollarBalance = rootRepository.getQuantityOfAssetInPortfolio("usd", 1).orElse(0.0);
        rootRepository.editPortfolio("usd", 1, bankDollarBalance + transactionCostsInDollar);

        if (buyerId == Globals.getBankId()) {
            rootRepository.updateBalanceByUserId(sellerId, rootRepository.getBalanceByUserId(sellerId) - transactionCosts);
        } else if (sellerId == Globals.getBankId()) {
            rootRepository.updateBalanceByUserId(buyerId, rootRepository.getBalanceByUserId(buyerId) - transactionCosts);
        } else {
            rootRepository.updateBalanceByUserId(buyerId, rootRepository.getBalanceByUserId(buyerId) - transactionCosts / 2);
            rootRepository.updateBalanceByUserId(sellerId, rootRepository.getBalanceByUserId(sellerId) - transactionCosts / 2);
        }
    }

}
