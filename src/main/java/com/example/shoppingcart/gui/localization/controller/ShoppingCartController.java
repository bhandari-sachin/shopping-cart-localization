package com.example.shoppingcart.gui.localization.controller;

import com.example.shoppingcart.gui.localization.UTF8Control;
import com.example.shoppingcart.gui.localization.model.CartCalculator;
import com.example.shoppingcart.gui.localization.model.CartItem;
import com.example.shoppingcart.console.localization.strategy.NormalPricing;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShoppingCartController {

    @FXML private ChoiceBox<String> choiceLanguage;
    @FXML private TextField txtNumItems;
    @FXML private VBox itemsContainer;
    @FXML private Label lblTotal;
    @FXML private Label lblLanguage;
    @FXML private Label lblNumItems;
    @FXML private Button btnGenerate;
    @FXML private Button btnCalculate;

    private ResourceBundle bundle;
    private final CartCalculator calculator = new CartCalculator(new NormalPricing());
    private final List<TextField[]> itemFields = new ArrayList<>();

    @FXML
    public void initialize() {
        bundle = ResourceBundle.getBundle("MessagesBundle", Locale.ENGLISH, new UTF8Control());

        choiceLanguage.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            switchLanguage(newV);
        });
    }

    private void switchLanguage(String lang) {
        Locale locale;
        switch (lang) {
            case "Finnish":
                locale = new Locale("fi", "FI");
                setLayoutDirection(false);
                break;
            case "Swedish":
                locale = new Locale("sv", "SE");
                setLayoutDirection(false);
                break;
            case "Japanese":
                locale = new Locale("ja", "JP");
                setLayoutDirection(false);
                break;
            case "Arabic":
                locale = new Locale("ar", "AR");
                setLayoutDirection(true);
                break;
            default:
                locale = new Locale("en", "US");
                setLayoutDirection(false);
        }
        bundle = ResourceBundle.getBundle("MessagesBundle", locale, new UTF8Control());
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
        lblLanguage.setText(bundle.getString("select.language"));
        lblNumItems.setText(bundle.getString("prompt.num.items"));
        btnGenerate.setText(bundle.getString("btn.generate.items"));
        btnCalculate.setText(bundle.getString("btn.calculate.total"));
        lblTotal.setText(bundle.getString("total.cost"));
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
            itemLabel.setText(bundle.getString("item.prompt") + " " + (i + 1));

            // Update prompt text on price and quantity fields
            itemFields.get(i)[0].setPromptText(bundle.getString("prompt.price"));
            itemFields.get(i)[1].setPromptText(bundle.getString("prompt.quantity"));
        }
    }

    @FXML
    public void generateItemFields() {
        itemsContainer.getChildren().clear();
        itemFields.clear();

        int numItems = Integer.parseInt(txtNumItems.getText());
        for (int i = 0; i < numItems; i++) {
            TextField price = new TextField();
            price.setPromptText(bundle.getString("prompt.price"));

            TextField quantity = new TextField();
            quantity.setPromptText(bundle.getString("prompt.quantity"));

            HBox row = new HBox(10, new Label(bundle.getString("item.prompt") + " " + (i + 1)), price, quantity);
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
        lblTotal.setText(bundle.getString("total.cost") + " " + total);
    }
}