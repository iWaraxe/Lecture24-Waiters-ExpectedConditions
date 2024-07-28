package com.coherentsolutions.java.webauto.section01.advanced;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

/**
 * This class demonstrates how to implement a custom polling mechanism
 * similar to Implicit Wait using FluentWait.
 */
public class Ex05CustomPollingWait {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Create a custom wait with polling
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class);

            // Use the custom wait to find an element
            WebElement element = wait.until(d -> d.findElement(By.id("example-id")));

            System.out.println("Element found using custom polling wait: " + element.getTagName());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}