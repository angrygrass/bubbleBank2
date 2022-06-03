package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientBankCryptoBalanceException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.InsufficientCustomerBalanceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if (!checkCustomerBalance(userId, assetCode, quantity)) {
            throw new InsufficientCustomerBalanceException();
        }
        if (!checkBankCryptoBalance(assetCode, quantity)) {
            throw new InsufficientBankCryptoBalanceException();
        }

        //todo - add quantity of asset to customer's portfolio, subtract quantity of asset from bank's portfolio,
        //todo - add price of assets to bank's balance, subtract price of assets from customer's balance,
        //todo - and add corresponding methods in RootRepository, PortfolioDao and BankAccountDao interfaces and classes





        return "Succesfully bought " + quantity + rootRepository.findAssetByCode(assetCode).orElse(null).getAssetName();


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
        Customer bank = (Customer) rootRepository.getUserById(1).orElse(null);
        System.out.println(bank.getPortfolio().toString());
        bank.setPortfolio(rootRepository.getPortfolioOfCustomer(bank));
        if (bank.getPortfolio().getAssetsOfUser() == null) {return false;}

        else if (quantity >
                bank.getPortfolio().getAssetsOfUser().get(rootRepository.findAssetByCode(assetCode).orElse(null))) {
            return false;
        } else {
            return true;
        }

    }

}
