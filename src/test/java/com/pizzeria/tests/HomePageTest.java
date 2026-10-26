package com.pizzeria.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
public class HomePageTest extends BaseTest{
    @Test 
    public void testSiteOpens() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl,"URL не может быть пустым");
        System.out.println("Успешно открыт сайт: " + currentUrl);

    }
    
}
