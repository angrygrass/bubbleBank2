package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hva.miw.c27.team1.cryptobanking.repository.dao.NewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Dto xx
 */
public class NewsDto {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(NewsDto.class);

    private int id = 0;
    @JsonProperty("link")
    private String url;

    public NewsDto(String url) {
        this.url = url;
    }

    public NewsDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "url=" + url +
                '}';
    }
}
