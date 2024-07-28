package com.coherentsolutions.java.webauto.section02.advanced;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class demonstrates how to combine multiple ExpectedConditions.
 */
public class Ex05CombiningConditions {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Create WebDriverWait instance
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Combine multiple conditions
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.titleIs("Example Domain"),
                    ExpectedConditions.presenceOfElementLocated(By.tagName("p")),
                    ExpectedConditions.urlContains("example.com")
            ));

            System.out.println("All combined conditions met!");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}