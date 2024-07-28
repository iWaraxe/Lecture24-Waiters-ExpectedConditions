package com.coherentsolutions.java.webauto.section03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

/**
 * This class demonstrates a custom wait for a collection of elements.
 */
public class Ex03ElementCollectionWait {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.example.com");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void elementCollectionTest() {
        By newsLinksLocator = By.cssSelector(".tabs-content__item");

        // Wait for 10 elements to be present
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
                d.findElements(newsLinksLocator).size() == 10
        );

        // Find all elements
        List<WebElement> newsLinks = driver.findElements(newsLinksLocator);

        // Assert that there are 10 elements
        Assert.assertEquals(newsLinks.size(), 10, "Expected 10 news links");

        // Check each element
        newsLinks.forEach(link -> {
            Assert.assertTrue(link.isDisplayed(), "Link should be displayed");
            Assert.assertTrue(link.getText().contains(" "), "Link text should contain a space");
        });
    }
}