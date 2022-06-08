package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcPortfolioDao implements PortfolioDao {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcPortfolioDao.");
    }

    @Override
    public Optional<Double> findQuantityOfAssetInPortfolio (String assetCode, int userId) {
        String sql = "SELECT quantityOfAsset FROM assetofcustomer WHERE assetCode = ? AND userId = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Double.class, assetCode, userId));
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return null;
        }
    }

    private PreparedStatement insertPortfolioStatement(Portfolio portfolio, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into assetofcustomer (assetCode, userId, quantityOfAsset) values (?, ?, ?)");
        for(Map.Entry<Asset, Double>  map : portfolio.getAssetsOfUser().entrySet()) {
            ps.setString(1, map.getKey().getAssetCode());
            ps.setInt(2, portfolio.getCustomer().getId());
            ps.setDouble(3, map.getValue());
        }
        return ps;
    }

    @Override
    public void save(Portfolio portfolio) {
        jdbcTemplate.update(connection -> insertPortfolioStatement(portfolio, connection));
    }

    @Override
    public Portfolio getPortfolio(Customer customer) {
        int userId = customer.getId();
        System.out.println(userId);
        List<Portfolio> portfolioList = jdbcTemplate.query
                ("SELECT * FROM assetofcustomer  WHERE userId = ?",
                        // hardcoded met 1 geeft resultaat terug, rest niet
                        new PortfolioRowMapper(), userId);
        if (portfolioList.size() < 1) {
            System.out.println("Geen assets in portfolio");
            return null;
        } else {
            return portfolioList.get(0);
        }
    }

    public Optional<Portfolio> findById(int id) {
        List<Portfolio> portfolioList =
                jdbcTemplate.query("SELECT * FROM assetofcustomer WHERE userId = ?",
                        new PortfolioRowMapper(),id);
        if (portfolioList.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(portfolioList.get(0));
        }
    }

    public void editPortfolio(String assetCode, int userId, double quantity) {
        String sql = "UPDATE `assetofcustomer` SET quantityofasset = ? WHERE userId = ? AND assetCode =?;";
        jdbcTemplate.update(sql, quantity, userId, assetCode);
    }

    public Optional<Boolean> isPresentInPortfolio(String assetCode, int userId) {
        String sql = "select * from assetofcustomer where assetCode = ? and userId = ?;";
        try {
            List<Portfolio> portfolioList = jdbcTemplate.query(sql, new PortfolioRowMapper(),
                                assetCode, userId
                                );

            if (!portfolioList.isEmpty())
            {return Optional.of(true);
            }
            else {return Optional.of(false);
            }
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return Optional.empty();
        }
    }

    public Asset populateAssetForPortfolio(String assetCode)  {
        List<Asset> assetList =
                jdbcTemplate.query("SELECT * FROM asset WHERE assetCode = ?",
                        new AssetRowMapper(), assetCode);
        if (assetList.size() < 1) {
            return null;
        } else {
            return assetList.get(0);
        }
    }

    // row mappers below
    /**
     * Used by method getPortfolio(Customer customer).
     * Retrieves rows from 3 columns in assetofcustomer via PortfolioRowMapper. Every asset that a customer
     * has is placed in a hash map. This hash map is used to make 1 portfolio. The portfolio is returned
     * in this method. The assetName is linked to the assetCode, and the total value of the portfolio is calculated.
     */
    private class PortfolioRowMapper implements RowMapper<Portfolio> {
        @Override
        public Portfolio mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            HashMap<Asset, Double> assetsOfUser = new HashMap<>();
            double valueOfOwnedAssets = 0;
            do {
                String assetCode = resultSet.getString("assetCode");
                int userId = resultSet.getInt("userId");
                double quantityOfAsset = Utility.roundDecimal(resultSet.getDouble("quantityOfAsset"), 2);
                String assetName = populateAssetForPortfolio(assetCode).getAssetName();
                double rateInEuro = populateAssetForPortfolio(assetCode).getRateInEuros();
                assetsOfUser.put(new Asset(assetCode, assetName,rateInEuro), quantityOfAsset);
                valueOfOwnedAssets += (quantityOfAsset * rateInEuro);
            } while (resultSet.next());

            JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcTemplate); // nodig?
            // relatie customer - portfolio niet bidirectioneel maken ?
            Portfolio portfolio = new Portfolio("EUR", assetsOfUser, new Customer(0, null,
                    null, null, 0, null, null
                    ,null, null, null, null, null, null,
                    null, null));
            portfolio.setValueOfOwnedAssets(Utility.roundDecimal(valueOfOwnedAssets,2));
            return portfolio;
        }
    }

    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Asset(rs.getString("assetCode"),
                    rs.getString("assetName"), rs.getDouble("rateInEuro"));
        }
    }


}





