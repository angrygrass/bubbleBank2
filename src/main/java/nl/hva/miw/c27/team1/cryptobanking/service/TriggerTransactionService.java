package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.TriggerTransaction;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.Globals;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TriggerTransactionService {

    private final RootRepository rootRepository;
    private final TransactionService transactionService;
    private final Logger logger = LogManager.getLogger(TriggerTransactionService.class);

    @Autowired
    public TriggerTransactionService(RootRepository rootRepository, TransactionService transactionService) {
        super();
        this.rootRepository = rootRepository;
        this.transactionService = transactionService;
        logger.info("New " + TriggerTransactionService.class.getName());
    }

    public Optional<TriggerTransaction> findByKey(String assetCode, int userId, boolean sellYesNo) {
        return rootRepository.findByKey(assetCode, userId, sellYesNo);
    }

    public void save(TriggerTransaction triggerTransaction) {
        //in case of selling, check crypto balance else check euro balance
        double transactionCostsInEuros = transactionService.getTransactionCostsInEuro(
                triggerTransaction.getAssetCode(), triggerTransaction.getQuantityToBuyOrSell());
        if (triggerTransaction.isSellYesNo()) {
            //check if customer bank account balance and bank crypto balance are sufficient for the transaction
            transactionService.doBalanceChecks(Globals.getBankId(), triggerTransaction.getUserId(),
                    triggerTransaction.getAssetCode(), triggerTransaction.getQuantityToBuyOrSell(),
                    transactionCostsInEuros);
        }
        else {
            transactionService.doBalanceChecks(triggerTransaction.getUserId(), Globals.getBankId(),
                    triggerTransaction.getAssetCode(), triggerTransaction.getQuantityToBuyOrSell(),
                    transactionCostsInEuros);
        }
        //no exceptions occurred, so we can save the order
        rootRepository.save(triggerTransaction);
    }

    public void delete(TriggerTransaction triggerTransaction) {
        rootRepository.delete(triggerTransaction);
    }

    public List<TriggerTransaction> getAll() {
        return rootRepository.getAll();
    }

    public void executeTriggerTransactions() {
        //loop through list, if conditions are met, perform transactions
        List<TriggerTransaction> ttList = getAll();
        for (TriggerTransaction tra: ttList) {
            //if sell, check that rate >= triggerRate, so check assets
            Asset asset =  rootRepository.findAssetByCode(tra.getAssetCode()).orElse(null);
            if (tra.isSellYesNo() && asset != null && asset.getRateInEuros() >= tra.getTriggerRate()) {
                transactionService.doTransaction(Globals.getBankId(), tra.getUserId(), tra.getAssetCode(),
                        tra.getQuantityToBuyOrSell());
                delete(tra);
            }
            //if buy, opposite check
            if (!tra.isSellYesNo() && asset != null && asset.getRateInEuros() <= tra.getTriggerRate()) {
                transactionService.doTransaction(tra.getUserId(), Globals.getBankId(), tra.getAssetCode(),
                        tra.getQuantityToBuyOrSell())  ;
                delete(tra);
            }

        }

    }
}
