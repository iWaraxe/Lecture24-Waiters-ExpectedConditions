package com.coherentsolutions.java.webauto.section01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * This class demonstrates the behavior of Implicit Wait in Selenium WebDriver.
 */
public class Ex03ImplicitWaitBehavior {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Navigate to a web page
            driver.get("https://www.example.com");

            // Try to find an element that doesn't exist
            long startTime = System.currentTimeMillis();
            try {
                WebElement nonExistentElement = driver.findElement(By.id("non-existent-id"));
            } catch (Exception e) {
                long endTime = System.currentTimeMillis();
                System.out.println("Time taken to throw exception: " + (endTime - startTime) / 1000.0 + " seconds");
                System.out.println("Exception: " + e.getClass().getSimpleName());
            }
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}