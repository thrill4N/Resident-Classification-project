package com.nkululeko.residentclassification.model;

/**
 * Abstract class Property - base class for SellProperty and RentProperty
 * @author (Nkululeko Khalishwayo)
 */
import java.io.Serializable;

public abstract class Property implements Calcable, Serializable, Comparable<Property>
{
    private String code;
    private String agentName;

    public Property()
    {
        this("", "");
    }

    public Property(String code, String agentName)
    {
        setCode(code);
        setAgent(agentName);
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setAgent(String agentName)
    {
        this.agentName = agentName;
    }

    public String getCode()
    {
        return code;
    }

    public String getAgent()
    {
        return agentName;
    }

    public int compareTo(Property other)
    {
        return this.code.compareTo(other.getCode());
    }

    public String toString()
    {
        return "Code: " + code + ", Agent: " + agentName;
    }
}
