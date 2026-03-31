package com.example.shoppingcart.console.localization.calculator;

import com.example.shoppingcart.console.localization.strategy.NormalPricing;
import com.example.shoppingcart.console.localization.strategy.PricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartCalculatorTest {

    private CartCalculator calculator;

    @BeforeEach
    void setUp() {
        PricingStrategy strategy = new NormalPricing();
        calculator = new CartCalculator(strategy);
    }

    @Test
    void testCalculateCartTotal_multipleItems() {
        double[] prices = {10.0, 5.0, 20.0};
        int[] quantities = {2, 3, 1};

        // Expected calculation: (10*2) + (5*3) + (20*1) = 55
        double expectedTotal = 55.0;

        double actualTotal = calculator.calculateCartTotal(prices, quantities);

        assertEquals(expectedTotal, actualTotal, 0.0001, "Total cart cost should match expected value");
    }

    @Test
    void testCalculateCartTotal_singleItem() {
        double[] prices = {15.0};
        int[] quantities = {4};

        // Expected calculation: 15*4 = 60
        double expectedTotal = 60.0;

        double actualTotal = calculator.calculateCartTotal(prices, quantities);

        assertEquals(expectedTotal, actualTotal, 0.0001, "Single item total should match expected value");
    }

    @Test
    void testCalculateCartTotal_emptyCart() {
        double[] prices = {};
        int[] quantities = {};

        double expectedTotal = 0.0;

        double actualTotal = calculator.calculateCartTotal(prices, quantities);

        assertEquals(expectedTotal, actualTotal, 0.0001, "Empty cart should return total 0");
    }

    @Test
    void testCalculateCartTotal_zeroQuantity() {
        double[] prices = {10.0, 5.0};
        int[] quantities = {0, 0};

        double expectedTotal = 0.0;

        double actualTotal = calculator.calculateCartTotal(prices, quantities);

        assertEquals(expectedTotal, actualTotal, 0.0001, "Items with zero quantity should not add to total");
    }

    @Test
    void testCalculateCartTotal_mismatchedArrays() {
        double[] prices = {10.0, 5.0};
        int[] quantities = {1}; // fewer quantities than prices

        // Should throw ArrayIndexOutOfBoundsException
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            calculator.calculateCartTotal(prices, quantities);
        });
    }
}