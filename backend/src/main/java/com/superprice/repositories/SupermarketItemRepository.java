package com.superprice.repositories;
import com.superprice.models.SupermarketItem;
import java.util.List;

public interface SupermarketItemRepository {
    public List<SupermarketItem> findAll();

    List<SupermarketItem> findBySupermarket(String supermarket);
    List<SupermarketItem> findByName(String name);
    List<SupermarketItem> findBySupermarketAndName(String supermarket, String name);
    List<SupermarketItem> findAllSortedByPrice(String order);
    List<SupermarketItem> findWithAllFilters(String supermarket, String name, String order, Double minPrice, Double maxPrice);
    List<SupermarketItem> findByPriceRange(Double minPrice, Double maxPrice);
}
