package com.example.shoppingcart.console.localization.strategy;

public class DiscountPricing implements PricingStrategy {
    @Override
    public double calculate(double price, int quantity) {
        return price * quantity * 0.9;
    }
}