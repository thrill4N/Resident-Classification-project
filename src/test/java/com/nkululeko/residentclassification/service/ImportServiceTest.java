package com.nkululeko.residentclassification.service;

/**
 * ImportServiceTest - unit tests for ImportService's file parsing logic.
 * @author (Nkululeko Khalishwayo)
 */
import com.nkululeko.residentclassification.model.Property;
import com.nkululeko.residentclassification.model.SellProperty;
import com.nkululeko.residentclassification.model.RentProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.PrintWriter;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ImportServiceTest
{
    private static final String TEST_FILE = "test_propertydata.txt";
    private final ImportService importService = new ImportService();

    @BeforeEach
    public void createTestFile() throws Exception
    {
        PrintWriter pw = new PrintWriter(new File(TEST_FILE));
        pw.println("1001#Thabo Nkosi#950000");
        pw.println("2001#Johan van der Merwe#8500#12");
        pw.println("1002#Anele Dlamini#1750000");
        pw.close();
    }

    @AfterEach
    public void deleteTestFile()
    {
        new File(TEST_FILE).delete();
    }

    @Test
    public void testParseFileReturnsCorrectCount()
    {
        List<Property> result = importService.parseFile(TEST_FILE);
        assertEquals(3, result.size());
    }

    @Test
    public void testParseFileClassifiesSellCorrectly()
    {
        List<Property> result = importService.parseFile(TEST_FILE);
        assertInstanceOf(SellProperty.class, result.get(0));
        assertEquals(950000, ((SellProperty) result.get(0)).getPrice(), 0.001);
    }

    @Test
    public void testParseFileClassifiesRentCorrectly()
    {
        List<Property> result = importService.parseFile(TEST_FILE);
        assertInstanceOf(RentProperty.class, result.get(1));
        assertEquals(12, ((RentProperty) result.get(1)).getDuration());
    }

    @Test
    public void testParseFileMissingFileReturnsEmptyList()
    {
        List<Property> result = importService.parseFile("does_not_exist.txt");
        assertTrue(result.isEmpty());
    }
}
