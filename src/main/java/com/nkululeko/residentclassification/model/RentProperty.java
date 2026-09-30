package com.nkululeko.residentclassification.model;

/**
 * RentProperty - a Property listed for rent at a monthly rate over a
 * fixed duration (in months).
 * @author (Nkululeko Khalishwayo)
 */
public class RentProperty extends Property
{
    private double rent;
    private int duration;

    public RentProperty()
    {
        this("", "", 0, 0);
    }

    public RentProperty(String code, String agentName, double rent, int duration)
    {
        super(code, agentName);
        setRent(rent);
        setDuration(duration);
    }

    public void setRent(double rent)
    {
        this.rent = rent;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public double getRent()
    {
        return rent;
    }

    public int getDuration()
    {
        return duration;
    }

    public double calculateAmount()
    {
        return rent * duration;
    }

    public String toString()
    {
        return super.toString() + ", Type: Rent, Rent: R" + rent + "/month, Duration: " + duration + " months";
    }
}
