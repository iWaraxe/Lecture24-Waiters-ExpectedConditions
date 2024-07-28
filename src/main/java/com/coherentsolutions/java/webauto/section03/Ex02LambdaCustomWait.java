package com.coherentsolutions.java.webauto.section03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class demonstrates a custom wait using a lambda expression.
 */
public class Ex02LambdaCustomWait {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Define the locator
            By newsLinksLocator = By.cssSelector(".tabs-content__item");

            // Use WebDriverWait with a lambda expression
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
                    d.findElements(newsLinksLocator).size() == 10
            );

            System.out.println("Lambda custom wait condition met!");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}