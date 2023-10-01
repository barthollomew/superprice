package com.superprice.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.superprice.models.SupermarketItem;
import com.superprice.repositories.SupermarketItemRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "v1/items")
public class SupermarketItemController {

    @Autowired
    private SupermarketItemRepository supermarketItemRepository;

    @GetMapping
    public List<SupermarketItem> getItems(@RequestParam(required = false) String supermarket,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) String order,
                                          @RequestParam(required = false) Double minPrice,
                                          @RequestParam(required = false) Double maxPrice) {

        // Validate price inputs
        if (minPrice != null && (minPrice < 0 || !isValidPriceFormat(minPrice))) {
            throw new IllegalArgumentException("Invalid minimum price format.");
        }
        if (maxPrice != null && (maxPrice < 0 || !isValidPriceFormat(maxPrice))) {
            throw new IllegalArgumentException("Invalid maximum price format.");
        }

        return supermarketItemRepository.findWithAllFilters(supermarket, search, order, minPrice, maxPrice);
    }

    // Utility function to check if the price format is valid (up to 2 decimal places)
    private boolean isValidPriceFormat(Double price) {
        String[] splitter = price.toString().split("\\.");
        if (splitter.length > 1) {
            return splitter[1].length() <= 2;
        }
        return true;
    }
}
