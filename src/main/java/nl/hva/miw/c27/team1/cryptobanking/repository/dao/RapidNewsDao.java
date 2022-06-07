package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.RapidNewsDto;

import java.util.List;

public interface RapidNewsDao {

    void saveArticles(List<RapidNewsDto> newsDto);
}
