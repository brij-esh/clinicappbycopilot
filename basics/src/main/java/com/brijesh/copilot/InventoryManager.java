package com.brijesh.copilot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InventoryManager class that adds, removes, and updates products.
 */
public class InventoryManager {
    private static final Logger LOGGER = Logger.getLogger(InventoryManager.class.getName());
    private final List<Product> products;

    public InventoryManager() {
        this.products = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param id       the product ID
     * @param name     the product name
     * @param quantity the product quantity
     */
    public synchronized void addProduct(int id, String name, int quantity) {
        Product product = new Product(id, name, quantity);
        products.add(product);
        LOGGER.log(Level.INFO, "Product added: {0}", product);
    }

    /**
     * Removes a product from the inventory by ID.
     *
     * @param id the product ID
     * @throws ProductNotFoundException if the product is not found
     */
    public synchronized void removeProduct(int id) {
        Product product = findProductById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        products.remove(product);
        LOGGER.log(Level.INFO, "Product removed: {0}", product);
    }

    /**
     * Updates the quantity of a product in the inventory.
     *
     * @param id       the product ID
     * @param quantity the new quantity
     * @throws ProductNotFoundException if the product is not found
     */
    public synchronized void updateProductQuantity(int id, int quantity) {
        Product product = findProductById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setQuantity(quantity);
        LOGGER.log(Level.INFO, "Product quantity updated: {0}", product);
    }

    /**
     * Returns a list of all products in the inventory.
     *
     * @return an unmodifiable list of products
     */
    public synchronized List<Product> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    /**
     * Lists all products in the inventory.
     */
    public synchronized void listProducts() {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            LOGGER.log(Level.INFO, "{0}. {1} - {2}", new Object[]{i + 1, product.getName(), product.getQuantity()});
        }
    }

    /**
     * Finds a product by ID.
     *
     * @param id the product ID
     * @return an Optional containing the product if found, or an empty Optional if not found
     */
    private Optional<Product> findProductById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    /**
     * Custom exception for product not found scenarios.
     */
    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
}