package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

public interface PortfolioDao {
    double findQuantityOfAssetInPortfolio (String assetCode, int userId);
}
