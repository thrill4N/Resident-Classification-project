package com.nkululeko.residentclassification.model;

/**
 * PropertyModelTest - unit tests for Property, SellProperty and RentProperty.
 * @author (Nkululeko Khalishwayo)
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertyModelTest
{
    @Test
    public void testSellPropertyFields()
    {
        SellProperty sp = new SellProperty("1001", "Thabo Nkosi", 950000);
        assertEquals("1001", sp.getCode());
        assertEquals("Thabo Nkosi", sp.getAgent());
        assertEquals(950000, sp.getPrice(), 0.001);
    }

    @Test
    public void testRentPropertyFields()
    {
        RentProperty rp = new RentProperty("2001", "Johan van der Merwe", 8500, 12);
        assertEquals("2001", rp.getCode());
        assertEquals("Johan van der Merwe", rp.getAgent());
        assertEquals(8500, rp.getRent(), 0.001);
        assertEquals(12, rp.getDuration());
    }

    @Test
    public void testSellPropertyCalculateAmountReturnsPrice()
    {
        SellProperty sp = new SellProperty("1002", "Anele Dlamini", 1750000);
        assertEquals(1750000, sp.calculateAmount(), 0.001);
    }

    @Test
    public void testRentPropertyCalculateAmountReturnsRentTimesDuration()
    {
        RentProperty rp = new RentProperty("2002", "Precious Mokoena", 6500, 6);
        assertEquals(39000, rp.calculateAmount(), 0.001);
    }

    @Test
    public void testNoArgConstructorsDoNotCrash()
    {
        SellProperty sp = new SellProperty();
        RentProperty rp = new RentProperty();
        assertEquals("", sp.getCode());
        assertEquals("", rp.getCode());
    }

    @Test
    public void testCompareToOrdersByCode()
    {
        SellProperty first = new SellProperty("1001", "Agent A", 100000);
        SellProperty second = new SellProperty("1002", "Agent B", 200000);
        assertTrue(first.compareTo(second) < 0);
        assertTrue(second.compareTo(first) > 0);
    }

    @Test
    public void testPolymorphicCalculateAmountThroughBaseReference()
    {
        Property p = new RentProperty("2003", "Naledi Sithole", 9500, 12);
        assertEquals(114000, p.calculateAmount(), 0.001);
    }
}
