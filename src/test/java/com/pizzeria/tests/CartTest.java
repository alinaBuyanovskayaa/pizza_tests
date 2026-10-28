package com.pizzeria.tests;

import com.pizzeria.pages.CartPage;
import com.pizzeria.pages.PizzaPage;
import org.testng.Assert; 
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test(description = "Добавление пиццы в корзину (ТЗ 3.2)") // <--- ИСПРАВЛЕНА АННОТАЦИЯ
    public void testAddToCart() {
        driver.get("https://pizzeria.skillbox.cc/product-category/menu/pizza/");
        PizzaPage pizzaPage = new PizzaPage(driver);

        double priceBefore = pizzaPage.getPriceByPizza();
        System.out.println("Цена на витрине: " + priceBefore);

        pizzaPage.addPizzaToCart();
        pizzaPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        double priceAfter = cartPage.getPriceInCart();
        System.out.println("Цена в корзине: " + priceAfter);

        // 5. 
        Assert.assertEquals(priceAfter, priceBefore, 
                "Цена в корзине не совпадает с ценой на витрине!");
    }
}