package com.pizzeria.tests;

import com.pizzeria.pages.HomePage;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(description = "Проверка кнопки 'Наверх' после скролла (ТЗ 3.1)")
    public void testUpButtonAppearsAfterScroll() {
        HomePage homePage = new HomePage(driver);
        homePage.scrollToBottom();
        Assert.assertTrue(homePage.isUpButtonVisible(), "Кнопка 'Наверх' не появилась!");
    }

    @Test(description = "Проверка переключения слайдера пицц (ТЗ 3.1)")
    public void testSliderRightArrow() {
        HomePage homePage = new HomePage(driver);
        String titleBefore = homePage.getActivePizzaTitle();
        homePage.clickRightArrow();
        homePage.waitForPizzaTitleToChange(titleBefore);
        String titleAfter = homePage.getActivePizzaTitle();
        Assert.assertNotEquals(titleBefore, titleAfter, "Слайдер не переключился!");
    }

    @Test(description = "Наведение на картинку напитка показывает кнопку 'В корзину' (ТЗ 3.1)")
    public void testDrinkHoverShowsAddToCart() {
        HomePage homePage = new HomePage(driver);
        
        homePage.hoverOverDrink();
        
        Assert.assertTrue(homePage.isAddToCartButtonVisible(), "Кнопка 'В корзину' не появилась!");
    }

    @Test (description = "Клик по картинке десерта открывает страницу десерта (ТЗ 3.1)")
    public void testDessertImageNavigation(){
        HomePage homePage = new HomePage(driver);
        String urlBefore = driver.getCurrentUrl();
        System.out.println("URL до клика: " + urlBefore);

        homePage.clickDessertImage();

        try{
            Thread.sleep(1000);

        } catch(InterruptedException e) {

        }

        String urlAfter = driver.getCurrentUrl();
        System.out.println("URL после клика: " + urlAfter);
      
        Assert.assertNotEquals(urlBefore, urlAfter, "URL не изменился после клика");
        Assert.assertTrue(urlAfter.contains("/product/"), 
                "Переход не на страницу товара! Текущий URL: " + urlAfter);
        }

        @DataProvider (name = "socialMediaData" )
        public Object [][] socialMediaData(){
            return new Object[][] {
                {"facebook", "https://www.facebook.com/skillboxru"},
                {"vk", "https://vk.ru/skillbox"},
                {"instagram", "https://www.instagram.com/skillbox.ru/"}
            };
        }

        @Test(dataProvider = "socialMediaData", description = "Клик по соцсети открывает новую вкладку (ТЗ 3.1)")
    public void testSocialMediaOpensNewTab(String socialMedia, String domain) {
        HomePage homepage = new HomePage(driver);
        String originalWindow = driver.getWindowHandle();
        int tabsBefore = driver.getWindowHandles().size();

        homepage.clickSocialMedia(socialMedia);

        wait.until(ExpectedConditions.numberOfWindowsToBe(tabsBefore + 1));

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            System.out.println("Вкладка: " + windowHandle + " -> URL: " + driver.getCurrentUrl());
        }

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.urlContains(domain));

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Вкладка " + socialMedia + " открылась с URL: " + currentUrl);
        
        Assert.assertTrue(currentUrl.contains(domain), 
                "Ссылка " + socialMedia + " ведет не туда! Ожидали: " + domain + ", а получили: " + currentUrl);

        driver.close();
        driver.switchTo().window(originalWindow);
    }



    }
