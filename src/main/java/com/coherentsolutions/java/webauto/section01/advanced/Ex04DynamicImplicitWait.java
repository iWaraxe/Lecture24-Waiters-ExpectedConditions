package com.coherentsolutions.java.webauto.section01.advanced;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * This class demonstrates how to dynamically change Implicit Wait duration.
 */
public class Ex04DynamicImplicitWait {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Set initial implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Navigate to a web page
            driver.get("https://www.example.com");

            // Perform some actions...

            // Change implicit wait duration
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Perform more actions...

            System.out.println("Implicit wait duration has been changed dynamically.");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}