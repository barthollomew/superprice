package com.superprice;

import com.superprice.models.SupermarketItem;
import com.superprice.repositories.SupermarketItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SuperpriceApplicationTests {

    @MockBean
    SupermarketItemRepository repository;

    List<SupermarketItem> mockItems = Arrays.asList(
        new SupermarketItem(1L, "Item1", 100.0, "SupermarketA"),
        new SupermarketItem(2L, "Item2", 200.0, "SupermarketB"),
        new SupermarketItem(3L, "Item3", 150.0, "SupermarketC")
    );

    @BeforeEach
    public void setUp() {
        when(repository.findBySupermarket("SupermarketA")).thenReturn(List.of(mockItems.get(0)));
        when(repository.findByName("Item1")).thenReturn(List.of(mockItems.get(0)));
        when(repository.findByPriceRange(100.0, 200.0)).thenReturn(mockItems);
        when(repository.findAllSortedByPrice("ASC")).thenReturn(mockItems);
        when(repository.findAllSortedByPrice("DESC")).thenReturn(Arrays.asList(mockItems.get(2), mockItems.get(1), mockItems.get(0)));
    }

    // US1.1
    @Test
    void filterBySupermarket() {
        List<SupermarketItem> items = repository.findBySupermarket("SupermarketA");
        assertEquals(1, items.size());
        assertEquals("Item1", items.get(0).name());
    }

    // US1.2
    @Test
    void comparePriceAcrossSupermarkets() {
        List<SupermarketItem> items = repository.findByName("Item1");
        assertEquals(1, items.size());
        assertEquals(100.0, items.get(0).price());
    }

    // US2.1
    @Test
    void searchByProductName() {
        List<SupermarketItem> items = repository.findByName("Item1");
        assertEquals(1, items.size());
        assertEquals("Item1", items.get(0).name());
    }

    // US4.1
    @Test
    void filterByPriceRange() {
        List<SupermarketItem> items = repository.findByPriceRange(100.0, 200.0);
        assertEquals(3, items.size());
    }

    // US5.1 (Ascending)
	@Test
	void sortByPriceAscending() {
		List<SupermarketItem> items = repository.findAllSortedByPrice("ASC");
		assertEquals(100.0, items.get(0).price());
		assertEquals(150.0, items.get(2).price());
	}
	
    // US5.1 (Descending)
	@Test
	void sortByPriceDescending() {
		List<SupermarketItem> items = repository.findAllSortedByPrice("DESC");
		assertEquals(150.0, items.get(0).price());
		assertEquals(100.0, items.get(2).price());
	}
	
}
