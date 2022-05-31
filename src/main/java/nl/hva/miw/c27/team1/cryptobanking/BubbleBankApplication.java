package nl.hva.miw.c27.team1.cryptobanking;

import nl.hva.miw.c27.team1.cryptobanking.service.CoinGeckoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
//@ComponentScan
public class BubbleBankApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BubbleBankApplication.class, args);
        CoinGeckoService.getRates();
    }




}
