package com.nkululeko.residentclassification.service;

/**
 * ImportService - reads Property records from a text file (same
 * format FileClass used) and saves them through PropertyRepository.
 * @author (Nkululeko Khalishwayo)
 */
import com.nkululeko.residentclassification.model.Property;
import com.nkululeko.residentclassification.model.SellProperty;
import com.nkululeko.residentclassification.model.RentProperty;
import com.nkululeko.residentclassification.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ImportService
{
    @Autowired
    private PropertyRepository repository;

    public List<Property> parseFile(String fileName)
    {
        List<Property> properties = new ArrayList<>();

        try
        {
            Scanner sc = new Scanner(new FileReader(fileName));

            while (sc.hasNext())
            {
                String line = sc.nextLine();
                String[] info = line.split("#");

                String code = info[0];
                String name = info[1];

                if (code.charAt(0) == '1')
                {
                    double price = Double.parseDouble(info[2]);
                    properties.add(new SellProperty(code, name, price));
                }
                else
                {
                    double rent = Double.parseDouble(info[2]);
                    int duration = Integer.parseInt(info[3]);
                    properties.add(new RentProperty(code, name, rent, duration));
                }
            }

            sc.close();
        }
        catch (IOException e)
        {
            System.out.println("Could not read file: " + e.getMessage());
        }

        return properties;
    }

    public int importAndSave(String fileName)
    {
        List<Property> properties = parseFile(fileName);
        repository.saveAll(properties);
        return properties.size();
    }
}
