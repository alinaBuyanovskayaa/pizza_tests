package com.pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

private final By priceByPizzaBascet = By.xpath("(//tr[contains(@class, 'cart_item')]//td[contains(@class, 'product-price')]//span[contains(@class, 'woocommerce-Price-amount')])[1]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public double getPriceInCart(){
        String text = waitForVisibility(priceByPizzaBascet).getText();
        String cleanText = text.replaceAll("[^0-9,]", "").replace(",", ".");
        return Double.parseDouble(cleanText);
    }

    
}
