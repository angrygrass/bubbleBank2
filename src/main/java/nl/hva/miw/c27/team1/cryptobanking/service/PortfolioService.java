package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LogManager.getLogger(PortfolioService.class);

    public PortfolioService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("new empty PortfolioService");
    }

    public Portfolio getPortfolio(Customer customer) {
        Portfolio portfolio = rootRepository.getPortfolioOfCustomer(customer);
        return portfolio;
    }



}
