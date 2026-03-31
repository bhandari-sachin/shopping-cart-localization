package com.example.shoppingcart.gui.localization.model;

import com.example.shoppingcart.console.localization.strategy.PricingStrategy;
import java.util.List;

/**
 * CartCalculator for GUI version.
 * Calculates total cost of items using a pricing strategy.
 */
public class CartCalculator {

    private final PricingStrategy strategy;

    public CartCalculator(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calculate the total cost of all items in the cart.
     *
     * @param items list of CartItem
     * @return total cost
     */
    public double calculateCartTotal(List<CartItem> items) {
        return items.stream()
                .mapToDouble(item -> strategy.calculate(item.getPrice(), item.getQuantity()))
                .sum();
    }

    /**
     * Calculate the total cost of a single item.
     *
     * @param item CartItem
     * @return total cost for that item
     */
    public double calculateItemTotal(CartItem item) {
        return strategy.calculate(item.getPrice(), item.getQuantity());
    }
}