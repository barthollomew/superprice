package com.superprice.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superprice.models.SupermarketItem;
import com.superprice.repositories.SupermarketItemRepository;

@Service
public class SupermarketItemServiceImpl implements SupermarketItemService {

    @Autowired
    private SupermarketItemRepository repository;

    @Override
    public Collection<SupermarketItem> getItems() {
        return repository.findAll();
    }
}