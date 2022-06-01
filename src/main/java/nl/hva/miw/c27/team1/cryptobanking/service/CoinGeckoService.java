package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hva.miw.c27.team1.cryptobanking.controller.api.BaseApiController;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class CoinGeckoService extends BaseApiController {

    private RootRepository rootRepository;
    private List<Asset> assetObjects;

    @JsonIgnore
    private static final Logger logger = LogManager.getLogger(CoinGeckoService.class);

    @Autowired
    public CoinGeckoService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("new empty CoingeckoService");
    }

    public CoinGeckoService() {
    }

    /**
     * Scheduled task for external api call (approx every 7 minutes). Instance of HTTPURLConnection makes
     * a connection for a HTTP GET request. Checks of the external server gives a 200 response.
     * If so, a Jackson mapper deserializes the array of json objects based on input stream, using only
     * id, symbol and current_price (see @JsonProperty in Asset).
     */
    @Scheduled(fixedRate = 400000)
    public void getRates() throws IOException {
           try {
            URL url = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&order=market_cap_desc&ids=bitcoin,solana,ethereum,litecoin,cardano,enjin,apecoin,tether,gas,tron,polkadot,waves,holo,loopring,polygon,dogecoin,tezos,chiliz,eos,uma");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.info(connection.getResponseMessage());
            } else {
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                assetObjects = mapper.readValue(connection.getInputStream(), new TypeReference<>(){});
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                // test in console
                for (Asset assets : assetObjects) {
                    System.out.println(assets);
                }
                //rootRepository.saveAssets(assetObjects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*
    @Scheduled(fixedRate = 400000)
    public void getRates() throws IOException {
        StringBuilder stringBuilder = null;
        try {
            URL url = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&order=market_cap_desc&ids=bitcoin,solana,ethereum,litecoin,cardano,enjin,apecoin,tether,gas,tron,polkadot,waves,holo,loopring,polygon,dogecoin,tezos,chiliz,eos,uma");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.info(connection.getResponseMessage());
            } else {
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();
                String inputLine;
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                while ((inputLine = buffReader.readLine()) != null) {
                    stringBuilder.append(inputLine + "\n");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            convertStringToObject(stringBuilder.toString());
            System.out.println(stringBuilder); // for check
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
    }*/

