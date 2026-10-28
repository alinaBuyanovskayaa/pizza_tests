package com.pizzeria.tests;

import com.pizzeria.pages.PizzaPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class PizzaPageTest extends BaseTest {

    @DataProvider(name = "sortingData") 
    public Object[][] sortingData() {
        return new Object[][]{
                {"По возрастанию цены", "orderby=price", true},
                {"По убыванию цены",    "orderby=price-desc", true},
                {"По популярности",     "orderby=popularity", false},
                {"Последние",           "orderby=date", false}
        };
    }

    @Test(dataProvider = "sortingData", description = "Сортировка пицц работает корректно (ТЗ 3.2)")
    public void testSorting(String visibleText, String urlParam, boolean checkMath) {
        driver.get("https://pizzeria.skillbox.cc/product-category/menu/pizza/");
        PizzaPage pizzaPage = new PizzaPage(driver);
        
        pizzaPage.selectSortBy(visibleText);
        
        String currentUrl = pizzaPage.getCurrentUrl();
        System.out.println("Проверяем сортировку: " + visibleText + " | URL: " + currentUrl);
        
        Assert.assertTrue(currentUrl.contains(urlParam), 
                "URL не содержит ожидаемый параметр " + urlParam);
        
        if (checkMath) {
            List<Double> prices = pizzaPage.getAllPrices();
            System.out.println("Цены: " + prices);
            for (int i = 0; i < prices.size() - 1; i++) {
                double currentPrice = prices.get(i);
                double nextPrice = prices.get(i + 1);
                
                if (visibleText.contains("возрастанию")) {
                    Assert.assertTrue(currentPrice <= nextPrice, "Ошибка по возрастанию!");
                } else if (visibleText.contains("убыванию")) {
                    Assert.assertTrue(currentPrice >= nextPrice, "Ошибка по убыванию!");
                }
            }
        }
    }

    @Test(description = "Фильтрация по цене (ТЗ 3.2)")
    public void testPriceFilter() {
        driver.get("https://pizzeria.skillbox.cc/product-category/menu/pizza/");
        PizzaPage pizzaPage = new PizzaPage(driver);
        
        pizzaPage.setFilter(30, -30);
        
        int expectedMin = pizzaPage.getMinPriceFromUrl();
        int expectedMax = pizzaPage.getMaxPriceFromUrl();
        System.out.println("Применен фильтр от " + expectedMin + " до " + expectedMax + " руб.");
        
        List<Double> prices = pizzaPage.getAllPrices();
        System.out.println("Цены после фильтрации: " + prices);
        
        Assert.assertFalse(prices.isEmpty(), "После фильтрации не осталось ни одной пиццы!");
        
        for (Double price : prices) {
            Assert.assertTrue(price >= expectedMin && price <= expectedMax, 
                    "Найдена цена вне диапазона [" + expectedMin + " - " + expectedMax + "]: " + price);
        }
    }
}