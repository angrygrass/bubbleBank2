package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;

import java.util.Optional;

public interface PortfolioDao {
    double findQuantityOfAssetInPortfolio (String assetCode, int userId);

    void save(Portfolio portfolio);

    Portfolio getPortfolio(Customer customer);

    Optional<Portfolio> findById(int id);




}
