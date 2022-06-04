package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.transfer.NewsDto;

import java.util.List;

public interface NewsDao {

    void saveArticles(List<NewsDto> newsDto);
}
