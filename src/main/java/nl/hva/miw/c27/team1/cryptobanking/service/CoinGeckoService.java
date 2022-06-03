package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hva.miw.c27.team1.cryptobanking.controller.api.BaseApiController;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.AssetHistoryDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class CoinGeckoService extends BaseApiController {

    private RootRepository rootRepository;
    private List<Asset> assetObjects;
    private List<AssetHistoryDto> assetHistoryObjects;
    private URL url = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&order=" +
            "market_cap_desc&ids=bitcoin,solana,ethereum,flow,cardano,dai,apecoin,tether,gas,tron,polkadot," +
            "waves,loopring,dogecoin,tezos,chiliz,eos,uma,radix,kusama");

    @JsonIgnore
    private static final Logger logger = LogManager.getLogger(CoinGeckoService.class);

    @Autowired
    public CoinGeckoService(RootRepository rootRepository) throws MalformedURLException {
        this.rootRepository = rootRepository;
        logger.info("new empty CoingeckoService");
    }

    public CoinGeckoService() throws MalformedURLException {
    }

    /**
     * Scheduled task for external api call (approx every X minutes). Instance of HTTPURLConnection makes
     * a connection for a HTTP GET request. Checks if the external server gives a 200 response.
     * If so, a Jackson mapper deserializes the array of json objects based on an input stream, populating an
     * Asset object only with the id, symbol and current_price of the Asset class (see @JsonProperty in Asset).
     */
    @Scheduled(fixedRate = 4000000)
    public void getRates() throws IOException {
           try {
               HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.info(connection.getResponseMessage());
            } else {
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                assetObjects = mapper.readValue(connection.getInputStream(), new TypeReference<>(){});
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                rootRepository.saveAssets(assetObjects);
                logger.info("@Scheduled: updated asset rates in database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Scheduled task to populate the assetratehistory table every X hours.
     */
    @Scheduled(cron = "0 0 */2 * * *")
    public void getHistoricRates() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.info(connection.getResponseMessage());
            } else {
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                assetHistoryObjects = mapper.readValue(connection.getInputStream(), new TypeReference<>() {
                });
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                rootRepository.saveAssetHistory(assetHistoryObjects);
                logger.info("@Scheduled: inserted historic asset rates in database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


