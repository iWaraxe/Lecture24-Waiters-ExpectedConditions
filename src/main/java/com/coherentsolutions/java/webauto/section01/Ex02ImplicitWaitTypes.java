package com.coherentsolutions.java.webauto.section01;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * This class demonstrates different types of Implicit Waits in Selenium WebDriver.
 */
public class Ex02ImplicitWaitTypes {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Set implicit wait for finding elements
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Set page load timeout
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            // Set script timeout (deprecated)
            // driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(5));

            // Navigate to a web page
            driver.get("https://www.example.com");

            System.out.println("Various implicit waits have been set.");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}