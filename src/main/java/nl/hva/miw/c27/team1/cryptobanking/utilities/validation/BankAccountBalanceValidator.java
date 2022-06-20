package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.Globals;

public class BankAccountBalanceValidator {
    public static boolean checkBankAccountBalance(int buyerId, int sellerId, String assetCode, double quantity,
                                                  double transactionCosts, RootRepository rootRepository) {
        if (buyerId != Globals.getBankId() && sellerId != Globals.getBankId()) {
            transactionCosts = transactionCosts / 2;
            double price = transactionCosts * (100 / rootRepository.getTransactionCosts());
            if (price * quantity + transactionCosts / 2 > rootRepository.getBalanceByUserId(buyerId)) {
                return false;
            } else {
                return true;
            }


        }
        if (buyerId == Globals.getBankId()) {transactionCosts = 0;}

        return !(rootRepository.findAssetByCode(assetCode).orElse(null).getRateInEuros() * quantity +
                transactionCosts > rootRepository.getBalanceByUserId(buyerId));
    }
}
