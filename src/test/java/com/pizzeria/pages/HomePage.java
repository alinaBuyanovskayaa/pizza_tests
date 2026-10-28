package com.pizzeria.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    
    private final By upButton = By.xpath("//*[@id='ak-top']");
    
    private final By rightArrow = By.xpath("//*[@id='accesspress_store_product-5']/ul/a[2]");
    private final By activePizzaTitle = By.xpath("//*[@id='accesspress_store_product-5']//li[contains(@class, 'slick-active')]//h3");
    
    private final By allDrinkImages = By.xpath("//*[@id='accesspress_store_product-7']//li//img");
    private final By allAddToCartButtons = By.xpath("//*[@id='accesspress_store_product-7']//li//a[contains(@class, 'add_to_cart_button')]");
    
    private final By dessertImage = By.xpath("//*[@id='accesspress_store_product-6']/ul/div/div/li[1]/div/a[1]/img");

    private final By facebookLink = By.xpath("//*[@id='accesspress_cta_simple-2']/div/div[1]/div/p[3]/a");
    private final By vkLink = By.xpath("//*[@id='accesspress_cta_simple-2']/div/div[1]/div/p[4]/a");
    private final By instagramLink = By.xpath("//*[@id='accesspress_cta_simple-2']/div/div[1]/div/p[5]/a");

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

    public void clickRightArrow() {
        WebElement arrow = waitForVisibility(rightArrow);
       ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrow);

    }

    public String getActivePizzaTitle() {
        return waitForVisibility(activePizzaTitle).getText();
    }

    public void waitForPizzaTitleToChange(String oldTitle) {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(activePizzaTitle, oldTitle)));
    }

    public void hoverOverDrink() {
        List<WebElement> drinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(allDrinkImages));
        for (WebElement drink : drinks) {
            try {
                new Actions(driver).moveToElement(drink).perform();
                return; 
            } catch (Exception e) {
            }
        }
        throw new RuntimeException("Не удалось найти видимую картинку напитка!");
    }

    public boolean isAddToCartButtonVisible() {
        try {
            List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(allAddToCartButtons));
            for (WebElement btn : buttons) {
                if (btn.isDisplayed()) {
                    return true; 
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickDessertImage() {
        WebElement dessert = wait.until(ExpectedConditions.presenceOfElementLocated(dessertImage));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dessert);
        waitForClickable(dessertImage).click();
    }

        public void clickSocialMedia(String socialMedia) {
        By locator;
        switch (socialMedia.toLowerCase()) {
            case "facebook":
                locator = facebookLink;
                break;
            case "vk":
                locator = vkLink;
                break;
            case "instagram":
                locator = instagramLink;
                break;
            default:
                throw new IllegalArgumentException("Неизвестная соц.сеть: " + socialMedia);
        }

        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
        
        System.out.println("Ссылка " + socialMedia + " ведёт на: " + link.getAttribute("href"));
        
        waitForClickable(locator).click();
    }
}