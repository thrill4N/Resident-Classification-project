package com.nkululeko.residentclassification.repository;

/**
 * PropertyRepository - handles saving and loading Property objects
 * using Spring's JdbcTemplate over a local SQLite database.
 * @author (Nkululeko Khalishwayo)
 */
import com.nkululeko.residentclassification.model.Property;
import com.nkululeko.residentclassification.model.SellProperty;
import com.nkululeko.residentclassification.model.RentProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS properties (" +
                     "code TEXT PRIMARY KEY, " +
                     "agentName TEXT, " +
                     "propertyType TEXT, " +
                     "price REAL, " +
                     "rent REAL, " +
                     "duration INTEGER)";
        jdbcTemplate.execute(sql);
    }

    public void insertProperty(Property p)
    {
        String sql = "INSERT OR REPLACE INTO properties (code, agentName, propertyType, price, rent, duration) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        if (p instanceof SellProperty sp)
        {
            jdbcTemplate.update(sql, sp.getCode(), sp.getAgent(), "SELL", sp.getPrice(), null, null);
        }
        else if (p instanceof RentProperty rp)
        {
            jdbcTemplate.update(sql, rp.getCode(), rp.getAgent(), "RENT", null, rp.getRent(), rp.getDuration());
        }
    }

    public void saveAll(List<Property> properties)
    {
        for (Property p : properties)
        {
            insertProperty(p);
        }
    }

    public List<Property> getAllProperties()
    {
        String sql = "SELECT * FROM properties";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
        {
            String code = rs.getString("code");
            String agentName = rs.getString("agentName");
            String propertyType = rs.getString("propertyType");

            if (propertyType.equals("SELL"))
            {
                return new SellProperty(code, agentName, rs.getDouble("price"));
            }
            else
            {
                return new RentProperty(code, agentName, rs.getDouble("rent"), rs.getInt("duration"));
            }
        });
    }

    public List<Property> getByType(String propertyType)
    {
        List<Property> all = new ArrayList<>();
        for (Property p : getAllProperties())
        {
            boolean isSell = p instanceof SellProperty;
            if ((propertyType.equals("SELL") && isSell) || (propertyType.equals("RENT") && !isSell))
            {
                all.add(p);
            }
        }
        return all;
    }

    public void updatePrice(String code, double newPrice)
    {
        jdbcTemplate.update("UPDATE properties SET price = ? WHERE code = ?", newPrice, code);
    }

    public void updateRent(String code, double newRent)
    {
        jdbcTemplate.update("UPDATE properties SET rent = ? WHERE code = ?", newRent, code);
    }

    public void deleteProperty(String code)
    {
        jdbcTemplate.update("DELETE FROM properties WHERE code = ?", code);
    }
}
