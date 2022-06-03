package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hva.miw.c27.team1.cryptobanking.model.Asset;
import nl.hva.miw.c27.team1.cryptobanking.model.BankAccount;
import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.model.Portfolio;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
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
            return Optional.of(jdbcTemplate.queryForObject(sql, Double.class, assetCode, userId));
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return null;
        }
    }

    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Asset(rs.getString("assetCode"),
                    rs.getString("assetName"), rs.getDouble("rateInEuro"));
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
        // lijst waar 1 portfolio in zit?
        List<Portfolio> assetList = jdbcTemplate.query
                ("SELECT * FROM assetofcustomer  WHERE userId = ?",
                        new JdbcPortfolioDao.PortfolioRowMapper(), userId);
        if (assetList.size() < 1) {
            System.out.println("Geen assets");
            return null;
        } else {
            // check if correct
            int listSize = assetList.size() -1;
            return assetList.get(-listSize);
        }
    }

    public Optional<Portfolio> findById(int id) {
        System.out.println("portfoliodao find by id");
        List<Portfolio> portfolioList =
                jdbcTemplate.query("SELECT * FROM assetofcustomer WHERE userId = ?",
                        new PortfolioRowMapper(), id);
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




    private class PortfolioRowMapper implements RowMapper<Portfolio> {
        @Override
        public Portfolio mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            System.out.println("kom je hier");




            HashMap<Asset, Double> assetsOfUser = new HashMap<>();
            while (resultSet.next()) {
                String assetCode = resultSet.getString("assetCode");
                int userId = resultSet.getInt("userId");
                double quantityOfAsset = resultSet.getDouble("quantityOfAsset");

                assetsOfUser.put(new Asset(assetCode), quantityOfAsset);
            }
            JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcTemplate);
            // check of casting is done correctly

            Portfolio portfolio =
                    new Portfolio("EUR", assetsOfUser, new Customer(0, null, null, null, 0, null, null
                    ,null, null, null, null, null, null, null, null));
            System.out.println("Portfolio rowmap");
            System.out.println(portfolio.getAssetsOfUser().toString());
            return portfolio;
        }
    }


}





