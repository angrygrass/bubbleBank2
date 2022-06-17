package nl.hva.miw.c27.team1.cryptobanking.repository.repository;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RapidNewsDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    private final RapidNewsDao newsDao;
    private final TransactionCostsDao transactionCostsDao;

    private final Logger logger = LogManager.getLogger(RootRepository.class);

    @Autowired
    public RootRepository(UserDao userDao, ProfileDao profileDao, BankAccountDao bankAccountDao, TokenDao tokenDao,
    TransactionDao transactionDao, AssetDao assetDao, PortfolioDao portfolioDao, AssetHistoryDao assetHistoryDao, RapidNewsDao newsDao,
    TransactionCostsDao transactionCostsDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
        this.bankAccountDao = bankAccountDao;
        this.tokenDao = tokenDao;
        this.transactionDao = transactionDao;
        this.assetDao = assetDao;
        this.portfolioDao = portfolioDao;
        this.assetHistoryDao = assetHistoryDao;
        this.newsDao = newsDao;
        this.transactionCostsDao = transactionCostsDao;
        logger.info("New RootRepository");
    }

    // methods for Customer
    public void saveCustomer(Customer customer) {
        userDao.save(customer);

        profileDao.save(customer.getProfile());

        bankAccountDao.save(customer.getBankAccount());

    }

    public Optional<Customer> updateUser(Customer customer) {return userDao.updateCustomer(customer);}


    // methods for User
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional <User> getUserById(int id) {
        Optional<User> user = userDao.findById(id);
        assert user.orElse(null) != null;
        user.orElse(null).setProfile(getProfileByUserId(id).orElse(null));

        return user;
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

    public Optional<Profile> getProfileByUserId(int userId) {return profileDao.findByUserId(userId);}

    // methods for Token
    public void saveToken(Token token) {tokenDao.save(token);}

    public Optional<Token> getTokenByUserId(int userId) {return tokenDao.findByUserId(userId);}

    public boolean checkIfExistsValidTokenForUser(User user) { return tokenDao.checkIfExistsValidTokenForUser(user);
    }

    public void revokeTokenFromUser(User user) {tokenDao.revokeToken(user);}

    // methods for Bank Account
    public Optional<BankAccount> findBankAccountByUserId (int id) {return bankAccountDao.findById(id);}

    public List<BankAccount> getAllBankAccounts() {return bankAccountDao.getAll();}

    public void updateBankAccountBalance(BankAccount bankAccount) {bankAccountDao.updateBalance(bankAccount);}

    public void deleteBankAccountByUserId(int userId) {bankAccountDao.deleteBankAccountByUserId(userId);}

    public Optional<BankAccount> findBankAccountByIban(String iban) {return bankAccountDao.findByIban(iban);}

    public double getBalanceByUserId(int userId) {return bankAccountDao.getBalanceByUserId(userId);}

    public void updateBalanceByUserId(int userId, double balance) {bankAccountDao.updateBalanceByUserId(userId, balance);}

    // methods for Transaction
    public Optional<Transaction> findTransactionById (int id) {return transactionDao.findById(id);}

    public int saveTransaction(Transaction transaction) {return transactionDao.save(transaction);}

    public List<Transaction> getAllTransactions() {return transactionDao.getAll();}

    // methods for Asset
    public void saveAsset(Asset asset) {assetDao.save(asset);}

    public void saveAssets(List<Asset> assetList) {
        assetDao.saveAllAssets(assetList);
    }

    public Optional<Asset> findAssetByCode (String code) {return assetDao.findByCode(code);}

    public Optional<Asset> findAssetByName (String name) {return assetDao.findByName(name);}

    public List<Asset> getAllAssets() {return assetDao.getAll();}

    public String getInvalidAssetMsg() {return assetDao.getInvalidAssetMsg();}

    // methods for Asset history
    public void saveAssetHistory(List<AssetHistoryDto> assetHistory) {
        assetHistoryDao.saveAssetHistoryList(assetHistory);
    }

    public Optional<List<AssetHistoryDto>> getAllHistoricAssetsDatabase(String assetCode, int numberDays) {
        System.out.println("testroot");
        return assetHistoryDao.getAllHistoricAssets(assetCode, numberDays);
    }
    // methods for RapidNewsService
    public void saveArticles(List<RapidNewsDto> articleList) {
        newsDao.saveArticles(articleList);
    }

    // methods for Portfolio
    public Optional<Double> getQuantityOfAssetInPortfolio(String assetCode, int userId) {
        return portfolioDao.findQuantityOfAssetInPortfolio(assetCode, userId); }

    public Portfolio getPortfolioOfCustomer(Customer customer) {

        Portfolio portfolio = portfolioDao.getPortfolio(customer);
        portfolio.setCustomer(customer);

        return portfolio;
    }

    public boolean checkAssetInPortfolio(String assetCode, int userId) {
        return assetPresentInPortfolio(assetCode, userId).orElse(false);
    }

    public void addToPortfolio(String assetCode, int userId, double quantity) {

        Portfolio portfolio = getPortfolioByCustomerId(userId).orElse(new Portfolio());
        HashMap<Asset, Double> assetsOfUser = portfolio.getAssetsOfUser();

        if (!checkAssetInPortfolio(assetCode, userId)) {
            assetsOfUser.put(findAssetByCode(assetCode).orElse(null), quantity);
            portfolio.setAssetsOfUser(assetsOfUser);
            portfolio.setCustomer((Customer) getUserById(userId).orElse(null));
            savePortfolio(portfolio);
        } else {
            double customerBalance = getQuantityOfAssetInPortfolio(assetCode, userId).orElse(0.0);
            editPortfolio(assetCode, userId, customerBalance + quantity);
        }

    }

    public void subtractFromPortfolio (int userId, String assetCode, double quantity) {

        double sellerBalance = getQuantityOfAssetInPortfolio(assetCode, userId).orElse(0.0);
        editPortfolio(assetCode, userId, sellerBalance - quantity);

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


    public LocalDateTime getLastAssetRateUpdate() {
        return assetDao.getLastAssetRateUpdate();
    }
}

