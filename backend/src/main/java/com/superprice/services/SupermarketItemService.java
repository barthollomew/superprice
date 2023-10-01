package com.superprice.services;

import java.util.Collection;

import com.superprice.models.SupermarketItem;

public interface SupermarketItemService {
    public Collection<SupermarketItem> getItems();
}