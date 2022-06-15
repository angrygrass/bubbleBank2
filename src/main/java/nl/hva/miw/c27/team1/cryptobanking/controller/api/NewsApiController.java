package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RapidNewsDto;
import nl.hva.miw.c27.team1.cryptobanking.service.RapidApiNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value=("/articles"))
public class NewsApiController extends BaseApiController {

    @Autowired
    public NewsApiController(RapidApiNewsService rapidApiNewsService) {
        super(rapidApiNewsService);
    }

    @GetMapping
    public List<RapidNewsDto> getAllArticles(@RequestHeader(value="authorization") String authorization) {
        try {
            return rapidApiNewsService.getArticles();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No articles found");
        }
    }


}

