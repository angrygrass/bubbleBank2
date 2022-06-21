package nl.hva.miw.c27.team1.cryptobanking.repository.dao;


import nl.hva.miw.c27.team1.cryptobanking.model.MarketplaceOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMarketplaceDao implements MarketplaceDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcMarketplaceDao.class);
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public JdbcMarketplaceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("new JdbcMarketplaceDao");

    }

    @Override
    public List<MarketplaceOffer> getAllOffers() {
        String sql = "SELECT * FROM marketplaceoffer;";
        return jdbcTemplate.query(sql, new JdbcMarketplaceDao.MarketplaceOfferRowMapper());

    }


    @Override
    public Optional<MarketplaceOffer> getOfferById(int id) {
        List<MarketplaceOffer> marketplaceoffers =
                jdbcTemplate.query("select * from marketplaceoffer where offerId = ?", new JdbcMarketplaceDao.MarketplaceOfferRowMapper(), id);
        if (marketplaceoffers.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(marketplaceoffers.get(0));
        }
    }

    @Override
    public MarketplaceOffer save(MarketplaceOffer marketplaceOffer) {
        String sql = "INSERT INTO marketplaceoffer(userId, dateTime,assetCode,quantity,price,sellYesOrNo,transactionPrice)" +
                " VALUES (?,?,?,?,?,?,?);";

        jdbcTemplate.update(sql,marketplaceOffer.getUserId(),marketplaceOffer.getDateTime(),
                marketplaceOffer.getAssetCode(),marketplaceOffer.getQuantity(),marketplaceOffer.getPrice(),
                marketplaceOffer.isSellYesOrNo(),marketplaceOffer.getTransactionPrice()
        );





        sql = "SELECT offerId FROM marketplaceoffer order by `dateTime` desc limit 1;";

        Integer offerId = jdbcTemplate.queryForObject(sql, Integer.class);
        if (offerId == null) {
            return null;
        } else {
            return getOfferById(offerId).orElse(null);
        }

    }

    @Override
    public void deleteOfferById(int id) {
        String sql = "DELETE FROM `marketplaceoffer` WHERE offerId = ?;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<MarketplaceOffer> editMarketplaceOffer(MarketplaceOffer marketplaceOffer) {

        String sql = "UPDATE `marketplaceoffer` SET userId = ?, dateTime = ?, assetCode = ?, quantity = ?," +
                "price = ?, sellYesOrNo = ?, transactionPrice = ?";
        jdbcTemplate.update(sql, marketplaceOffer.getUserId(), marketplaceOffer.getDateTime(), marketplaceOffer.getAssetCode(),
                marketplaceOffer.getQuantity(),
                marketplaceOffer.getPrice(), marketplaceOffer.isSellYesOrNo(), marketplaceOffer.getTransactionPrice());
        return Optional.of(marketplaceOffer);

    }

    private static class MarketplaceOfferRowMapper implements RowMapper<MarketplaceOffer> {
        @Override
        public MarketplaceOffer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MarketplaceOffer(rs.getInt("offerId"),
                    rs.getInt("userId"),
                    rs.getObject(3, LocalDateTime.class),
                    rs.getString("assetCode"),
                    rs.getDouble("quantity"),
                    rs.getDouble("price"),
                    rs.getBoolean("sellYesOrNo"),
                    rs.getDouble("transactionPrice"));
        }
    }

}
