package nl.hva.miw.c27.team1.cryptobanking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RapidNewsDto;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class RapidApiNewsService {

    private RootRepository rootRepository;
    private List<RapidNewsDto> articleList;
    private final String rapidApiKey = "6945d9a517msh8b1d924b670b723p1fc407jsn96e412329fb7";
    private int id = 1;

    @JsonIgnore
    private static final Logger logger = LogManager.getLogger(RapidApiNewsService.class);

    @Autowired
    public RapidApiNewsService(RootRepository rootRepository) throws MalformedURLException {
        this.rootRepository = rootRepository;
        logger.info("new News");
    }

    public RapidApiNewsService() {
    }

    /**
     * Calls external news API and gathers 5 recent articles on crypto topics. The API service expects an API key,
     * so this is added to the header of the request through .setRequestProperty.
     * Not part of requirements.
     */
    @Scheduled(fixedRate = 90000000)
    public void getArticles() throws IOException {
        try {
            URL url = new URL("https://free-news.p.rapidapi.com/v1/search?q=crypto&lang=en&page_size=5");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-RapidAPI-Key", rapidApiKey);
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    logger.info(connection.getResponseMessage());
                } else {
                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    JsonNode jsonNode = mapper.readTree(connection.getInputStream());
                    String sourceString = jsonNode.at("/articles").toString();
                    articleList = mapper.readValue(sourceString, new TypeReference<>(){});
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                        for (RapidNewsDto articleLink : articleList) {
                            articleLink.setId(id);
                            id++;
                        }
                    rootRepository.saveArticles(articleList);
                    logger.info("@Scheduled: updated articles in database");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
