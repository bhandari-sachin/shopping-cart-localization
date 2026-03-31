package com.example.shoppingcart.console.localization.strategy;

public interface PricingStrategy {
    double calculate(double price, int quantity);
}