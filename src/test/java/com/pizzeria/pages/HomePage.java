package com.pizzeria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    
    private final By upButton = By.xpath("//*[@id='ak-top']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public boolean isUpButtonVisible() {
        try {
            return waitForVisibility(upButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}