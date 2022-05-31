package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.controller.api.BaseApiController;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.commons.logging.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CoinGeckoService extends BaseApiController {

    private RootRepository rootRepository;

    @JsonIgnore
    private static final Logger logger = LogManager.getLogger(CoinGeckoService.class);

    public CoinGeckoService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("new empty CoingeckoService");
    }

    /**
     * Scheduled task for external api call (approx every 2 hours). Instantance of HTTPURLConnection makes
     * a connection for a HTTP GET request. Checks of the external server gives a 200 response.
     * If so, the bufferedReader will read the json characters of the response body and append these
     *  to a Stringbuilder object.
     */
    @Scheduled(fixedRate = 8000000)
    public static void getRates() throws IOException {
        StringBuilder stringBuilder = null;
        try {
            URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,solana," +
                    "       ethereum, litecoin,cardano,enjin,apecoin,tether,gas,tron,polkadot,waves,holo," +
                    "loopring,polygon,dogecoin,tezos,chiliz,eos,uma&vs_currencies=eur");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.info(connection.getResponseMessage());
            } else {
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();
                String inputLine;
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                while ((inputLine = buffReader.readLine()) != null) {
                    stringBuilder.append(inputLine + "\n");
                }
                System.out.println(stringBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        convertStringToObject(stringBuilder);
    }

    public static void convertStringToObject(StringBuilder stringBuilder) {


    }


}





    // String url = "https://api.coincap.io/v2/rates/" + coinName;
/*    @GetMapping(value="/rates/{coin}")
    public ResponseEntity<?> getRateOfCrypto(@PathVariable("coin") String coinName) {
        try {
            String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + coinName + "&vs_currencies=eur";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            assetService.updateAsset(result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
