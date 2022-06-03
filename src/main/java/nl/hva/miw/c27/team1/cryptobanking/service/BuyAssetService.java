package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientBankCryptoBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientCustomerBalanceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //check if customer bank account balance and bank crypto balance are sufficient for the transaction

        if (!checkCustomerBalance(userId, assetCode, quantity)) {
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

        // edit the bank's portfolio

        double bankBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, 1).orElse(0.0);
        rootRepository.editPortfolio(assetCode, 1, bankBalance - quantity);

        // edit customer and bank's bank account balance

        editCustomerBankAccount(assetCode, quantity, userId);

        editBankBankAccount(assetCode, quantity);

        return "Succesfully bought " + quantity + rootRepository.findAssetByCode(assetCode).orElse(null).getAssetName();


    }

    private void editCustomerBankAccount (String assetCode, double quantity, int userId) {

        BankAccount customerBankAccount = rootRepository.findBankAccountByUserId(userId).orElse(null);
        customerBankAccount.setCustomer((Customer) rootRepository.getUserById(userId).orElse(null));
        customerBankAccount.setBalanceInEuros(customerBankAccount.getBalanceInEuros() - quantity *
                rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros());
        rootRepository.updateBankAccountBalance(customerBankAccount);

    }


    private void editBankBankAccount (String assetCode, double quantity) {
        BankAccount bankBankAccount = rootRepository.findBankAccountByUserId(1).orElse(null);
        bankBankAccount.setCustomer((Customer) rootRepository.getUserById(1).orElse(null));
        bankBankAccount.setBalanceInEuros(bankBankAccount.getBalanceInEuros() + quantity *
                rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros());
        rootRepository.updateBankAccountBalance(bankBankAccount);

    }



    private boolean checkCustomerBalance(int userId, String assetCode, double quantity) {
        Customer customer = (Customer) rootRepository.getUserById(userId).orElse(null);
        customer.setBankAccount(rootRepository.findBankAccountByUserId(userId).orElse(null));
        System.out.println(rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() * quantity);
        System.out.println(customer.getBankAccount().getIban());
        System.out.println(customer.getBankAccount().getBalanceInEuros());
        if (rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() * quantity >
                customer.getBankAccount().getBalanceInEuros()) {
            return false;
        }
        return true;

    }
    private boolean checkBankCryptoBalance(String assetCode, double quantity) {

        double bankBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, 1).orElse(0.0);


        if (quantity >
                bankBalance) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkAssetInPortfolio(String assetCode, int userId) {
        if (rootRepository.getQuantityOfAssetInPortfolio(assetCode, userId).isPresent()) {
            return true;
        }
        return false;
    }

}
