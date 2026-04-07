package com.example.shoppingcart.gui.localization.controller;

import com.example.shoppingcart.gui.localization.db.LocalizationService;
import com.example.shoppingcart.gui.localization.model.CartCalculator;
import com.example.shoppingcart.gui.localization.model.CartItem;
import com.example.shoppingcart.console.localization.strategy.NormalPricing;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCartController {

    @FXML private ChoiceBox<String> choiceLanguage;
    @FXML private TextField txtNumItems;
    @FXML private VBox itemsContainer;
    @FXML private Label lblTotal;
    @FXML private Label lblLanguage;
    @FXML private Label lblNumItems;
    @FXML private Button btnGenerate;
    @FXML private Button btnCalculate;

    private final LocalizationService localizationService = new LocalizationService();
    private Map<String, String> messages;
    private String currentLanguageCode = "en";
    private final CartCalculator calculator = new CartCalculator(new NormalPricing());
    private final List<TextField[]> itemFields = new ArrayList<>();

    @FXML
    public void initialize() {
        messages = localizationService.getStrings(currentLanguageCode);

        choiceLanguage.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            switchLanguage(newV);
        });

        choiceLanguage.getSelectionModel().select("English");
        updateLabels();
        Platform.runLater(this::updateLabels);
    }

    private void switchLanguage(String lang) {
        if (lang == null) {
            return;
        }

        boolean rtl = false;
        switch (lang) {
            case "Finnish":
                currentLanguageCode = "fi";
                break;
            case "Swedish":
                currentLanguageCode = "sv";
                break;
            case "Japanese":
                currentLanguageCode = "ja";
                break;
            case "Arabic":
                currentLanguageCode = "ar";
                rtl = true;
                break;
            default:
                currentLanguageCode = "en";
        }
        setLayoutDirection(rtl);
        messages = localizationService.getStrings(currentLanguageCode);
        updateLabels();
    }

    private void setLayoutDirection(boolean rtl) {
        if (itemsContainer.getScene() != null) {
            itemsContainer.getScene().getRoot().setNodeOrientation(
                    rtl ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT
            );
        }
    }

    private void updateLabels() {
        lblLanguage.setText(messages.getOrDefault("select.language", "Select Language:"));
        lblNumItems.setText(messages.getOrDefault("prompt.num.items", "Enter number of items:"));
        btnGenerate.setText(messages.getOrDefault("btn.generate.items", "Generate Items"));
        btnCalculate.setText(messages.getOrDefault("btn.calculate.total", "Calculate Total"));
        lblTotal.setText(messages.getOrDefault("total.cost", "Total cost:"));

        if (itemsContainer.getScene() != null && itemsContainer.getScene().getWindow() instanceof Stage stage) {
            stage.setTitle(messages.getOrDefault("app.title", "Shopping Cart"));
        }

        refreshItemRows();
    }

    /**
     * Updates item row labels AND prompt text when language changes.
     * Loops through each HBox row in itemsContainer and updates
     * the Label (e.g. "Item 1") and both TextField prompt texts.
     */
    private void refreshItemRows() {
        for (int i = 0; i < itemsContainer.getChildren().size(); i++) {
            HBox row = (HBox) itemsContainer.getChildren().get(i);

            // First child of each HBox is the "Item N" label — update it
            Label itemLabel = (Label) row.getChildren().get(0);
            itemLabel.setText(messages.getOrDefault("item.prompt", "Item") + " " + (i + 1));

            // Update prompt text on price and quantity fields
            itemFields.get(i)[0].setPromptText(messages.getOrDefault("prompt.price", "Price"));
            itemFields.get(i)[1].setPromptText(messages.getOrDefault("prompt.quantity", "Quantity"));
        }
    }

    @FXML
    public void generateItemFields() {
        itemsContainer.getChildren().clear();
        itemFields.clear();

        int numItems = Integer.parseInt(txtNumItems.getText());
        for (int i = 0; i < numItems; i++) {
            TextField price = new TextField();
            price.setPromptText(messages.getOrDefault("prompt.price", "Price"));

            TextField quantity = new TextField();
            quantity.setPromptText(messages.getOrDefault("prompt.quantity", "Quantity"));

            HBox row = new HBox(10, new Label(messages.getOrDefault("item.prompt", "Item") + " " + (i + 1)), price, quantity);
            itemsContainer.getChildren().add(row);

            itemFields.add(new TextField[]{price, quantity});
        }
    }

    @FXML
    public void calculateTotal() {
        List<CartItem> items = new ArrayList<>();

        for (TextField[] fields : itemFields) {
            double price    = Double.parseDouble(fields[0].getText());
            int    quantity = Integer.parseInt(fields[1].getText());
            items.add(new CartItem(price, quantity));
        }

        double total = calculator.calculateCartTotal(items);
        lblTotal.setText(messages.getOrDefault("total.cost", "Total cost:") + " " + total);
    }
}