package com.coherentsolutions.java.webauto.section02.advanced;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class demonstrates how to create and use a custom ExpectedCondition.
 */
public class Ex04CustomExpectedCondition {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Create WebDriverWait instance
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Define custom ExpectedCondition
            ExpectedCondition<Boolean> customCondition = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    WebElement element = driver.findElement(By.tagName("p"));
                    return element.getText().length() > 50;
                }
            };

            // Wait for custom condition
            wait.until(customCondition);

            System.out.println("Custom condition met!");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}