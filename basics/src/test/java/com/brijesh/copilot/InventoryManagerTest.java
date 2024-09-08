package com.brijesh.copilot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {
    private InventoryManager inventoryManager;

    @BeforeEach
    public void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Test
    public void testAddProduct() {
        inventoryManager.addProduct(1, "Product 1", 10);
        List<Product> products = inventoryManager.getProducts();
        assertEquals(1, products.size());
        assertEquals(1, products.get(0).getId());
        assertEquals("Product 1", products.get(0).getName());
        assertEquals(10, products.get(0).getQuantity());
    }

    @Test
    public void testRemoveProduct() {
        inventoryManager.addProduct(1, "Product 1", 10);
        inventoryManager.removeProduct(1);
        List<Product> products = inventoryManager.getProducts();
        assertTrue(products.isEmpty());
    }

    @Test
    public void testRemoveProductNotFound() {
        Exception exception = assertThrows(InventoryManager.ProductNotFoundException.class, () -> {
            inventoryManager.removeProduct(1);
        });
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void testUpdateProductQuantity() {
        inventoryManager.addProduct(1, "Product 1", 10);
        inventoryManager.updateProductQuantity(1, 20);
        List<Product> products = inventoryManager.getProducts();
        assertEquals(20, products.get(0).getQuantity());
    }

    @Test
    public void testUpdateProductQuantityNotFound() {
        Exception exception = assertThrows(InventoryManager.ProductNotFoundException.class, () -> {
            inventoryManager.updateProductQuantity(1, 20);
        });
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void testGetProducts() {
        inventoryManager.addProduct(1, "Product 1", 10);
        inventoryManager.addProduct(2, "Product 2", 20);
        List<Product> products = inventoryManager.getProducts();
        assertEquals(2, products.size());
        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
    }

    @Test
    public void testListProducts() {
        inventoryManager.addProduct(1, "Product 1", 10);
        inventoryManager.addProduct(2, "Product 2", 20);
        inventoryManager.listProducts();
        // Assuming the logger prints to the console, you would need to capture the console output to verify it.
        // This is a simplified example and may need adjustment based on your logging setup.
    }
}