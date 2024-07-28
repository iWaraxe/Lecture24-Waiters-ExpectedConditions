package com.coherentsolutions.java.webauto.section03.advanced;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

/**
 * This class demonstrates a custom wait using FluentWait.
 */
public class Ex04FluentWaitCustom {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to a web page
            driver.get("https://www.example.com");

            // Create a custom Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class);

            // Use the custom wait
            WebElement element = wait.until(d -> {
                WebElement el = d.findElement(By.id("dynamicElement"));
                return el.isDisplayed() ? el : null;
            });

            System.out.println("Element found: " + element.getText());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}