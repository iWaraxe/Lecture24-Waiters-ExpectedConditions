package com.coherentsolutions.java.webauto.section03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class demonstrates a basic custom wait using ExpectedCondition.
 */
public class Ex01BasicCustomWait {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Define the locator
            By newsLinksLocator = By.cssSelector(".tabs-content__item");

            // Create a custom wait condition
            ExpectedCondition<Boolean> customCondition = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return driver.findElements(newsLinksLocator).size() == 10;
                }
            };

            // Use WebDriverWait with the custom condition
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(customCondition);

            System.out.println("Custom wait condition met!");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}