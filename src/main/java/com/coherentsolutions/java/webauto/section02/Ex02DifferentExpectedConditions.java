package com.coherentsolutions.java.webauto.section02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class demonstrates different types of ExpectedConditions in Explicit Waits.
 */
public class Ex02DifferentExpectedConditions {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Create WebDriverWait instance
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for title
            wait.until(ExpectedConditions.titleIs("Example Domain"));

            // Wait for element to be clickable
            By buttonLocator = By.id("submitButton");
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

            // Wait for text to be present in element
            By paragraphLocator = By.tagName("p");
            wait.until(ExpectedConditions.textToBePresentInElementLocated(paragraphLocator, "This domain is for use in illustrative examples"));

            System.out.println("All conditions met!");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}