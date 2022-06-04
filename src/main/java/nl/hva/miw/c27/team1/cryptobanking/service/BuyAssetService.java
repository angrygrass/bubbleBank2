package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientBankCryptoBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientCustomerBalanceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BuyAssetService {
    private RootRepository rootRepository;
    private final Logger logger = LogManager.getLogger(BuyAssetService.class);

    @Autowired
    public BuyAssetService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("new BuyAssetService");
    }

    public String buyFromBank(int userId, String assetCode, double quantity) {

        //calculate transaction costs in euro
        double transactionCostsInEuros = rootRepository.getTransactionCosts() * quantity *
                rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() / 100;


        //check if customer bank account balance and bank crypto balance are sufficient for the transaction

        if (!checkCustomerBalance(userId, assetCode, quantity, transactionCostsInEuros)) {
            throw new InsufficientCustomerBalanceException();
        }
        if (!checkBankCryptoBalance(assetCode, quantity)) {
            throw new InsufficientBankCryptoBalanceException();
        }



        //load customer portfolio

        Portfolio portfolio = rootRepository.getPortfolioByCustomerId(userId).orElse(new Portfolio());
        HashMap<Asset, Double> assetsOfUser = portfolio.getAssetsOfUser();


        //check if asset is already in customer's portfolio - if not save the new asset + amount, else update current amount

        if (!checkAssetInPortfolio(assetCode, userId)) {
            assetsOfUser.put(rootRepository.findAssetByCode(assetCode).orElse(null), quantity);
            portfolio.setAssetsOfUser(assetsOfUser);
            portfolio.setCustomer((Customer) rootRepository.getUserById(userId).orElse(null));
            rootRepository.savePortfolio(portfolio);
        } else {
            double customerBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, userId).orElse(0.0);
            rootRepository.editPortfolio(assetCode, userId, customerBalance + quantity);
        }

        // edit the bank's portfolio (subtracting the quantity of the bought asset and adding the transaction costs
        // to the bank's dollar balance (after conversion of course)

        editBankPortfolio (assetCode, quantity, transactionCostsInEuros);


        // edit customer and bank's bank account balance

        editCustomerBankAccount(assetCode, quantity, userId, transactionCostsInEuros);

        editBankBankAccount(assetCode, quantity);

        // save transaction to database

        rootRepository.saveTransaction(new Transaction(1, quantity, rootRepository.findAssetByCode(assetCode).
                orElse(null).getRateInEuros(), LocalDateTime.now(), transactionCostsInEuros, userId, 1,
                assetCode));



        return "Succesfully bought " + quantity + " " + rootRepository.findAssetByCode(assetCode).orElse(null).
                getAssetName() + " for €" + quantity *
                rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() + ", of which €" +
                transactionCostsInEuros + " are transaction costs.";


    }

    private void editBankPortfolio (String assetCode, double quantity, double transactionCosts) {




        double bankBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, 1).orElse(0.0);
        rootRepository.editPortfolio(assetCode, 1, bankBalance - quantity);
        double transactionCostsInDollar = transactionCosts * (1 / rootRepository.findAssetByCode("USD").orElse(null).
                getRateInEuros());

        double bankDollarBalance = rootRepository.getQuantityOfAssetInPortfolio("usd", 1).orElse(0.0);


        rootRepository.editPortfolio("usd", 1, bankDollarBalance + transactionCostsInDollar);

    }

    private void editCustomerBankAccount (String assetCode, double quantity, int userId, double transactionCosts) {


        BankAccount customerBankAccount = rootRepository.findBankAccountByUserId(userId).orElse(null);
        customerBankAccount.setCustomer((Customer) rootRepository.getUserById(userId).orElse(null));
        customerBankAccount.setBalanceInEuros(customerBankAccount.getBalanceInEuros() - quantity *
                rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() - transactionCosts);
        rootRepository.updateBankAccountBalance(customerBankAccount);

    }


    private void editBankBankAccount (String assetCode, double quantity) {

        BankAccount bankBankAccount = rootRepository.findBankAccountByUserId(1).orElse(null);
        bankBankAccount.setCustomer((Customer) rootRepository.getUserById(1).orElse(null));
        bankBankAccount.setBalanceInEuros(bankBankAccount.getBalanceInEuros() + quantity *
                rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros());
        rootRepository.updateBankAccountBalance(bankBankAccount);

    }



    private boolean checkCustomerBalance(int userId, String assetCode, double quantity, double transactionCosts) {
        Customer customer = (Customer) rootRepository.getUserById(userId).orElse(null);
        customer.setBankAccount(rootRepository.findBankAccountByUserId(userId).orElse(null));


        if (rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() * quantity +
                transactionCosts > customer.getBankAccount().getBalanceInEuros()) {
            return false;
        }
        return true;

    }
    private boolean checkBankCryptoBalance(String assetCode, double quantity) {

        if (!checkAssetInPortfolio(assetCode, 1)) {
            return false;
        }
        double bankBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, 1).orElse(0.0);

        if (quantity >
                bankBalance) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkAssetInPortfolio(String assetCode, int userId) {
        return rootRepository.assetPresentInPortfolio(assetCode, userId).orElse(false);
    }

}
