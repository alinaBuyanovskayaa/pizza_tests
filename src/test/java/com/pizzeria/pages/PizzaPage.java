package com.pizzeria.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class PizzaPage extends BasePage {
    
    private final By sortDropDown = By.xpath("//*[@id='primary']/div/form/select");
    
    private final By allPrices = By.xpath("//ul[contains(@class, 'products')]//span[contains(@class, 'woocommerce-Price-amount')]");
    
    private final By minSliderHandle = By.xpath("(//div[contains(@class, 'price_slider')]/span)[1]");
    private final By maxSliderHandle = By.xpath("(//div[contains(@class, 'price_slider')]/span)[2]");
    private final By applyFilterButton = By.xpath("//*[@id='woocommerce_price_filter-2']/form/div/div[2]/button");

    private final By priceByPizza = By.xpath("//*[@id='primary']/div/div[3]/ul/li[1]/div[2]/div/span");
    private final By buttonPizza = By.xpath("(//ul[contains(@class, 'products')]//a[contains(@class, 'add_to_cart_button')])[1]");
    private final By bascet = By.xpath("//*[@id='menu-item-29']/a");


    public PizzaPage(WebDriver driver) {
        super(driver);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public void selectSortBy(String visibleText) {
        Select select = new Select(waitForClickable(sortDropDown));
        select.selectByVisibleText(visibleText);
        wait.until(ExpectedConditions.visibilityOfElementLocated(allPrices));
    } 

    public List<Double> getAllPrices() {
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allPrices));
        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String text = element.getText();
            String cleanText = text.replaceAll("[^0-9,]", "").replace(",", ".");
            if (!cleanText.isEmpty()) {
                prices.add(Double.parseDouble(cleanText));
            }
        }
        return prices;
    }

    public void setFilter(int minOffSet, int maxOffSet) {
        Actions actions = new Actions(driver);
        
        WebElement minHandle = wait.until(ExpectedConditions.presenceOfElementLocated(minSliderHandle));
        WebElement maxHandle = wait.until(ExpectedConditions.presenceOfElementLocated(maxSliderHandle));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", minHandle);
        
        actions.moveToElement(minHandle).clickAndHold().moveByOffset(minOffSet, 0).release().perform();
        actions.moveToElement(maxHandle).clickAndHold().moveByOffset(maxOffSet, 0).release().perform();

        waitForClickable(applyFilterButton).click();
        wait.until(ExpectedConditions.urlContains("min_price"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(allPrices));
    }

    public int extractPriceFromUrl(String paramName) {
        String currentUrl = driver.getCurrentUrl();
        Pattern pattern = Pattern.compile(paramName + "=(\\d+)");
        Matcher matcher = pattern.matcher(currentUrl);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
    
    public int getMinPriceFromUrl() {
        return extractPriceFromUrl("min_price");
    }

    public int getMaxPriceFromUrl() {
        return extractPriceFromUrl("max_price");
    }

   public double getPriceByPizza(){
    String text = waitForVisibility(priceByPizza).getText();
    String cleanText = text.replaceAll("[^0-9,]", "").replace(",", ".");
    return Double.parseDouble(cleanText);
   } 

   public void addPizzaToCart(){
    WebElement addButton = waitForClickable(buttonPizza);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
    wait.until(ExpectedConditions.attributeContains(buttonPizza, "class", "added"));

   }

   public void goToCart(){
    waitForClickable(bascet).click();
    wait.until(ExpectedConditions.urlContains("cart"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[contains(@class, 'cart_item')]")));   }

}