package com.nkululeko.residentclassification.repository;

/**
 * PropertyRepositoryTest - integration tests for PropertyRepository,
 * run against a real SQLite database (test-properties.db, configured
 * in src/test/resources/application.properties) via the Spring context.
 * @author (Nkululeko Khalishwayo)
 */
import com.nkululeko.residentclassification.model.Property;
import com.nkululeko.residentclassification.model.SellProperty;
import com.nkululeko.residentclassification.model.RentProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PropertyRepositoryTest
{
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void clearTable()
    {
        jdbcTemplate.execute("DELETE FROM properties");
    }

    @AfterEach
    public void clearTableAgain()
    {
        jdbcTemplate.execute("DELETE FROM properties");
    }

    @Test
    public void testInsertAndRetrieveSellProperty()
    {
        repository.insertProperty(new SellProperty("1001", "Thabo Nkosi", 950000));
        List<Property> all = repository.getAllProperties();

        assertEquals(1, all.size());
        assertInstanceOf(SellProperty.class, all.get(0));
        assertEquals(950000, ((SellProperty) all.get(0)).getPrice(), 0.001);
    }

    @Test
    public void testInsertAndRetrieveRentProperty()
    {
        repository.insertProperty(new RentProperty("2001", "Johan van der Merwe", 8500, 12));
        List<Property> all = repository.getAllProperties();

        assertEquals(1, all.size());
        assertInstanceOf(RentProperty.class, all.get(0));
        assertEquals(12, ((RentProperty) all.get(0)).getDuration());
    }

    @Test
    public void testGetByTypeFiltersCorrectly()
    {
        repository.insertProperty(new SellProperty("1001", "Thabo Nkosi", 950000));
        repository.insertProperty(new RentProperty("2001", "Johan van der Merwe", 8500, 12));

        assertEquals(1, repository.getByType("SELL").size());
        assertEquals(1, repository.getByType("RENT").size());
    }

    @Test
    public void testUpdatePriceChangesStoredValue()
    {
        repository.insertProperty(new SellProperty("1001", "Thabo Nkosi", 950000));
        repository.updatePrice("1001", 999000);

        List<Property> all = repository.getAllProperties();
        assertEquals(999000, ((SellProperty) all.get(0)).getPrice(), 0.001);
    }

    @Test
    public void testDeletePropertyRemovesIt()
    {
        repository.insertProperty(new SellProperty("1001", "Thabo Nkosi", 950000));
        repository.deleteProperty("1001");

        assertTrue(repository.getAllProperties().isEmpty());
    }

    @Test
    public void testInsertOrReplaceOverwritesExistingCode()
    {
        repository.insertProperty(new SellProperty("1001", "Thabo Nkosi", 950000));
        repository.insertProperty(new SellProperty("1001", "Thabo Nkosi", 1000000));

        List<Property> all = repository.getAllProperties();
        assertEquals(1, all.size());
        assertEquals(1000000, ((SellProperty) all.get(0)).getPrice(), 0.001);
    }
}
