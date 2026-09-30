package com.nkululeko.residentclassification.model;

/**
 * SellProperty - a Property listed for sale at a fixed price.
 * @author (Nkululeko Khalishwayo)
 */
public class SellProperty extends Property
{
    private double price;

    public SellProperty()
    {
        this("", "", 0);
    }

    public SellProperty(String code, String agentName, double price)
    {
        super(code, agentName);
        setPrice(price);
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }

    public double calculateAmount()
    {
        return price;
    }

    public String toString()
    {
        return super.toString() + ", Type: Sell, Price: R" + price;
    }
}
