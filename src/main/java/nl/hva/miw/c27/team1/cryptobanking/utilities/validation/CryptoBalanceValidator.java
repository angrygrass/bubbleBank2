package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;

public class CryptoBalanceValidator {
    public static boolean checkCryptoBalance(int sellerId, String assetCode, double quantity,
                                             RootRepository rootRepository) {
        if (!rootRepository.checkAssetInPortfolio(assetCode, sellerId)) {
            return false;
        }
        double cryptoBalance = rootRepository.getQuantityOfAssetInPortfolio(assetCode, sellerId).orElse(0.0);
        return !(quantity >
                cryptoBalance);
    }

}
