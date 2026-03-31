package com.example.shoppingcart.console.localization;


import com.example.shoppingcart.console.localization.calculator.CartCalculator;
import com.example.shoppingcart.console.localization.factory.LocaleFactory;
import com.example.shoppingcart.console.localization.service.MessageService;
import com.example.shoppingcart.console.localization.strategy.NormalPricing;
import com.example.shoppingcart.console.localization.strategy.PricingStrategy;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //  Language selection
        System.out.println("Select language (en, fi, sv, ja): ");
        String lang = scanner.nextLine().trim();

        Locale locale = LocaleFactory.getLocale(lang);
        MessageService.init(locale);

        //  Show welcome message
        System.out.println(MessageService.get("welcome"));

        //  Initialize pricing strategy and calculator
        PricingStrategy strategy = new NormalPricing();
        CartCalculator calculator = new CartCalculator(strategy);

        int numItems = 0;

        // Get number of items (with input validation)
        while (true) {
            try {
                System.out.println(MessageService.get("prompt.num.items"));
                String input = scanner.nextLine();
                numItems = Integer.parseInt(input);

                if (numItems <= 0) {
                    System.out.println(MessageService.get("error.positive.number"));
                } else break;
            } catch (NumberFormatException e) {
                System.out.println(MessageService.get("error.invalid.number"));
            }
        }

        double[] prices = new double[numItems];
        int[] quantities = new int[numItems];

        // Loop through each item
        for (int i = 0; i < numItems; i++) {

            System.out.println(MessageService.get("item.prompt") + " " + (i + 1));

            // Get price
            while (true) {
                try {
                    System.out.println(MessageService.get("prompt.price"));
                    String priceInput = scanner.nextLine();
                    prices[i] = Double.parseDouble(priceInput);

                    if (prices[i] <= 0) {
                        System.out.println(MessageService.get("error.positive.number"));
                    } else break;
                } catch (NumberFormatException e) {
                    System.out.println(MessageService.get("error.invalid.number"));
                }
            }

            // Get quantity
            while (true) {
                try {
                    System.out.println(MessageService.get("prompt.quantity"));
                    String qtyInput = scanner.nextLine();
                    quantities[i] = Integer.parseInt(qtyInput);

                    if (quantities[i] <= 0) {
                        System.out.println(MessageService.get("error.positive.number"));
                    } else break;
                } catch (NumberFormatException e) {
                    System.out.println(MessageService.get("error.invalid.number"));
                }
            }

            // Show item total
            double itemTotal = strategy.calculate(prices[i], quantities[i]);
            System.out.println(MessageService.get("item.added") + " " + itemTotal);
        }

        //  Calculate cart total
        double total = calculator.calculateCartTotal(prices, quantities);

        // Show total
        System.out.println(MessageService.get("total.cost") + " " + total);
        System.out.println(MessageService.get("items.count") + " " + numItems);

        scanner.close();
    }
}