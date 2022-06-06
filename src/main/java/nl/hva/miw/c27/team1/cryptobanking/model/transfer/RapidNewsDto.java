package nl.hva.miw.c27.team1.cryptobanking.model.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dto with 1 @Jsonproperty to filter the url link out of the json response body.
 */
public class RapidNewsDto {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RapidNewsDto.class);

    private int id = 0;
    @JsonProperty("link")
    private String url;

    public RapidNewsDto(String url) {
        this.url = url;
    }

    public RapidNewsDto() {
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
