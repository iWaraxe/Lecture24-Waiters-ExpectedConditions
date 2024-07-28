package com.coherentsolutions.java.webauto.section03.advanced;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * This class demonstrates a custom wait with timeout and custom exception.
 */
public class Ex05CustomWaitWithTimeout {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Use custom wait method
            WebElement element = waitForElement(driver, By.id("dynamicElement"), Duration.ofSeconds(10));

            System.out.println("Element found: " + element.getText());
        } catch (CustomTimeoutException e) {
            System.out.println("Custom wait timed out: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    /**
     * Custom wait method with timeout.
     */
    private static WebElement waitForElement(WebDriver driver, By locator, Duration timeout) throws CustomTimeoutException {
        long end = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < end) {
            WebElement element = driver.findElement(locator);
            if (element != null && element.isDisplayed()) {
                return element;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CustomTimeoutException("Wait interrupted", e);
            }
        }
        throw new CustomTimeoutException("Element not found within the specified timeout");
    }

    /**
     * Custom exception for wait timeout.
     */
    private static class CustomTimeoutException extends Exception {
        public CustomTimeoutException(String message) {
            super(message);
        }

        public CustomTimeoutException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}