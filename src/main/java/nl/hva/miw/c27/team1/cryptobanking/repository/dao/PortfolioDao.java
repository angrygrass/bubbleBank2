package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;

import java.util.Optional;

public interface PortfolioDao {
    Optional<Double> findQuantityOfAssetInPortfolio (String assetCode, int userId);

    void save(Portfolio portfolio);

    Portfolio getPortfolio(Customer customer);

    Optional<Portfolio> findById(int id);

    void editPortfolio(String assetCode, int userId, double quantity);




}
