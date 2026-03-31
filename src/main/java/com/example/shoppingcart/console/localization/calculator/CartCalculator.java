package com.example.shoppingcart.console.localization.calculator;


import com.example.shoppingcart.console.localization.strategy.PricingStrategy;

public class CartCalculator {

    private final PricingStrategy strategy;

    public CartCalculator(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateCartTotal(double[] prices, int[] quantities) {
        double total = 0;

        for (int i = 0; i < prices.length; i++) {
            total += strategy.calculate(prices[i], quantities[i]);
        }

        return total;
    }
}
