package com.nkululeko.residentclassification.controller;

/**
 * PropertyController - thin REST layer exposing CRUD operations on
 * properties. Contains no business logic - delegates everything to
 * PropertyRepository.
 * @author (Nkululeko Khalishwayo)
 */
import com.nkululeko.residentclassification.model.Property;
import com.nkululeko.residentclassification.model.SellProperty;
import com.nkululeko.residentclassification.model.RentProperty;
import com.nkululeko.residentclassification.repository.PropertyRepository;
import com.nkululeko.residentclassification.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController
{
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ImportService importService;

    @GetMapping
    public List<Property> getAll()
    {
        return repository.getAllProperties();
    }

    @PostMapping("/import")
    public String importFromFile(@RequestParam(defaultValue = "propertydata.txt") String file)
    {
        int count = importService.importAndSave(file);
        return count + " properties imported.";
    }

    @GetMapping("/sell")
    public List<Property> getSellProperties()
    {
        return repository.getByType("SELL");
    }

    @GetMapping("/rent")
    public List<Property> getRentProperties()
    {
        return repository.getByType("RENT");
    }

    @PostMapping("/sell")
    public void addSellProperty(@RequestBody SellProperty property)
    {
        repository.insertProperty(property);
    }

    @PostMapping("/rent")
    public void addRentProperty(@RequestBody RentProperty property)
    {
        repository.insertProperty(property);
    }

    @PutMapping("/sell/{code}/price")
    public void updatePrice(@PathVariable String code, @RequestParam double price)
    {
        repository.updatePrice(code, price);
    }

    @PutMapping("/rent/{code}/rent")
    public void updateRent(@PathVariable String code, @RequestParam double rent)
    {
        repository.updateRent(code, rent);
    }

    @DeleteMapping("/{code}")
    public void deleteProperty(@PathVariable String code)
    {
        repository.deleteProperty(code);
    }
}
