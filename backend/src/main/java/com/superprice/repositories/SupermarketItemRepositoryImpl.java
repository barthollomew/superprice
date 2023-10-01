package com.superprice.repositories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.stereotype.Repository;

import com.superprice.models.SupermarketItem;
// Repository for Supermarket Items
@Repository
public class SupermarketItemRepositoryImpl implements SupermarketItemRepository {
    // Data source for database connect
    @Autowired
    private DataSource dataSource;
    // This fetches all items from the database
    @Override
    public List<SupermarketItem> findAll() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM items;");
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findAll", e);
        }
    }
    // This fetches all items by supermarket
    @Override
    public List<SupermarketItem> findBySupermarket(String supermarket) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM items WHERE supermarket = ?;");
            stm.setString(1, supermarket);
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findBySupermarket", e);
        }
    }
    // This fetches all items by name
    @Override
    public List<SupermarketItem> findByName(String name) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM items WHERE name LIKE ?;");
            stm.setString(1, "%" + name + "%");
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findByName", e);
        }
    }
    // This fetches all items by both supermarket and name
    @Override
    public List<SupermarketItem> findBySupermarketAndName(String supermarket, String name) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM items WHERE supermarket = ? AND name LIKE ?;");
            stm.setString(1, supermarket);
            stm.setString(2, "%" + name + "%");
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findBySupermarketAndName", e);
        }
    }
    // This fetches all items and sorts them by price
    @Override
    public List<SupermarketItem> findAllSortedByPrice(String order) {
        String query = "SELECT * FROM items ORDER BY price " + (order.equals("DESC") ? "DESC" : "ASC") + ";";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findAllSortedByPrice", e);
        }
    }
    
    // This fetches all items using all filters
    @Override
    public List<SupermarketItem> findWithAllFilters(String supermarket, String name, String order, Double minPrice, Double maxPrice) {
        try {
            Connection connection = dataSource.getConnection();
            StringBuilder query = new StringBuilder("SELECT * FROM items WHERE 1=1 ");
            
            if (supermarket != null && !supermarket.isEmpty()) {
                query.append("AND supermarket = ? ");
            }
            if (name != null && !name.isEmpty()) {
                query.append("AND name LIKE ? ");
            }
            if (minPrice != null) {
                query.append("AND price >= ? ");
            }
            if (maxPrice != null) {
                query.append("AND price <= ? ");
            }
            if (order != null && (order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))) {
                query.append("ORDER BY price ").append(order);
            }
            
            PreparedStatement stm = connection.prepareStatement(query.toString());
            
            int paramIndex = 1;
            if (supermarket != null && !supermarket.isEmpty()) {
                stm.setString(paramIndex++, supermarket);
            }
            if (name != null && !name.isEmpty()) {
                stm.setString(paramIndex++, "%" + name + "%");
            }
            if (minPrice != null) {
                stm.setDouble(paramIndex++, minPrice);
            }
            if (maxPrice != null) {
                stm.setDouble(paramIndex++, maxPrice);
            }
            
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findWithAllFilters", e);
        }
    }
    
    // This fetches all items  within a price range
    @Override
    public List<SupermarketItem> findByPriceRange(Double minPrice, Double maxPrice) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM items WHERE price BETWEEN ? AND ?;");
            stm.setDouble(1, minPrice);
            stm.setDouble(2, maxPrice);
            List<SupermarketItem> items = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SupermarketItem item = new SupermarketItem(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                items.add(item);
            }
            connection.close();
            return items;
        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in findByPriceRange", e);
        }
    }
 
}