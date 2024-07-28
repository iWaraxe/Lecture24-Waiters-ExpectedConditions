package com.coherentsolutions.java.webauto.section02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class demonstrates the basic usage of Explicit Wait in Selenium WebDriver.
 */
public class Ex01BasicExplicitWait {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Create WebDriverWait instance
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for an element to be visible
            By locator = By.id("exampleId");
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            System.out.println("Element is now visible!");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}