package com.pizzeria.tests;

import org.testng.annotations.Test;

import com.pizzeria.pages.HomePage;

import org.testng.Assert;
public class HomePageTest extends BaseTest{
    @Test 
    public void testSiteOpens() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl,"URL не может быть пустым");
        System.out.println("Успешно открыт сайт: " + currentUrl);

    }

    @Test 
    public void testUpbutton(){
        HomePage homePage = new HomePage(driver);
        homePage.scrollToBottom();
        Assert.assertTrue(homePage.isUpButtonVisible(), "Кнопка 'Наверх' не появилась после скролла вниз");
    }
    
}
