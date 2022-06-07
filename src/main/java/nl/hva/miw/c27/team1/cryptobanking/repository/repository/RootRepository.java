package nl.hva.miw.c27.team1.cryptobanking.repository.repository;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// class needs db queries
@Repository
public class RootRepository {

    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final BankAccountDao bankAccountDao;
    private final TokenDao tokenDao;
    private final TransactionDao transactionDao;
    private final AssetDao assetDao;
    private final PortfolioDao portfolioDao;
    private final AssetHistoryDao assetHistoryDao;
    private final TransactionCostsDao transactionCostsDao;

    private final Logger logger = LogManager.getLogger(RootRepository.class);

    @Autowired
    public RootRepository(UserDao userDao, ProfileDao profileDao, BankAccountDao bankAccountDao, TokenDao tokenDao,
    TransactionDao transactionDao, AssetDao assetDao, PortfolioDao portfolioDao, AssetHistoryDao assetHistoryDao,
    TransactionCostsDao transactionCostsDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
        this.bankAccountDao = bankAccountDao;
        this.tokenDao = tokenDao;
        this.transactionDao = transactionDao;
        this.assetDao = assetDao;
        this.portfolioDao = portfolioDao;
        this.assetHistoryDao = assetHistoryDao;
        this.transactionCostsDao = transactionCostsDao;
        logger.info("New RootRepository");
    }

    // methods for Customer
    public void saveCustomer(Customer customer) {
        userDao.save(customer);
        profileDao.save(customer.getProfile());
        bankAccountDao.save(customer.getBankAccount());
        //todo - portfolio.save
    }

    public Optional<Customer> updateUser(Customer customer) {return userDao.updateCustomer(customer);}


    // methods for User
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional <User> getUserById(int id) {


        return userDao.findById(id);
    }

    public Optional<User> getUserByToken(Token token) {return userDao.findByToken(token);}

    public Optional<User> getUserByRole(String role) {return userDao.getByRole(role);}

    public void deleteUser(int id) {userDao.deleteUserById(id);}

    // methods for Admin
    public void saveAdmin(Admin admin) {
        userDao.save(admin);
        profileDao.save(admin.getProfile());
    }

    // methods for Profile
    public Optional<Profile> getProfileByUsername(String username) {return profileDao.findByUserName(username);}

    // methods for Token
    public void saveToken(Token token) {tokenDao.save(token);}

    public Optional<Token> getTokenByUserId(int userId) {return tokenDao.findByUserId(userId);}

    public void revokeTokenFromUser(User user) {tokenDao.revokeToken(user);}

    // methods for Bank Account
    public Optional<BankAccount> findBankAccountByUserId (int id) {return bankAccountDao.findById(id);}

    public List<BankAccount> getAllBankAccounts() {return bankAccountDao.getAll();}

    public void updateBankAccountBalance(BankAccount bankAccount) {bankAccountDao.updateBalance(bankAccount);}

    public void deleteBankAccountByUserId(int userId) {bankAccountDao.deleteBankAccountByUserId(userId);}

    public Optional<BankAccount> findBankAccountByIban(String iban) {return bankAccountDao.findByIban(iban);}

    // methods for Transaction
    public Optional<Transaction> findTransactionById (int id) {return transactionDao.findById(id);}

    public void saveTransaction(Transaction transaction) {transactionDao.save(transaction);}

    public List<Transaction> getAllTransactions() {return transactionDao.getAll();}

    // methods for Asset
    public void saveAsset(Asset asset) {assetDao.save(asset);}

    public void saveAssets(List<Asset> assetList) {
        assetDao.saveAllAssets(assetList);
    }

    public Optional<Asset> findAssetByCode (String code) {return assetDao.findByCode(code);}

    public Optional<Asset> findAssetByName (String name) {return assetDao.findByName(name);}

    public List<Asset> getAllAssets() {return assetDao.getAll();}

    // methods for Asset history
    public void saveAssetHistory(List<AssetHistoryDto> assetHistory) {
        assetHistoryDao.saveAssetHistoryList(assetHistory);
    }


    // methods for Portfolio
    public Optional<Double> getQuantityOfAssetInPortfolio(String assetCode, int userId) {
        return portfolioDao.findQuantityOfAssetInPortfolio(assetCode, userId); }

    public Portfolio getPortfolioOfCustomer(Customer customer) {
        return portfolioDao.getPortfolio(customer);
    }

    public Optional<Portfolio> getPortfolioByCustomerId(int id) {
        return portfolioDao.findById(id);}

    public void savePortfolio(Portfolio portfolio) {portfolioDao.save(portfolio);}

    public void editPortfolio(String assetCode, int userId, double quantity) {portfolioDao.editPortfolio(assetCode,
            userId, quantity);}

    public Optional<Boolean> assetPresentInPortfolio(String assetCode, int userId) {return portfolioDao.isPresentInPortfolio(
            assetCode, userId);}

    // methods for transaction costs

    public double getTransactionCosts() {return transactionCostsDao.get();}
    public void editTransactionCosts(double transactionCosts) {transactionCostsDao.edit(transactionCosts);}

    // getters & setters
    public UserDao getUserDao() {
        return userDao;
    }

    public ProfileDao getProfileDao() {
        return profileDao;
    }

    public BankAccountDao getBankAccountDao() {
        return bankAccountDao;
    }

    public TokenDao getTokenDao() {
        return tokenDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }

    public AssetDao getAssetDao() {
        return assetDao;
    }

    public Logger getLogger() {
        return logger;
    }



}

