package com.coherentsolutions.java.webauto.section01;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * This class demonstrates the basic usage of Implicit Wait in Selenium WebDriver.
 */
public class Ex01BasicImplicitWait {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Navigate to a web page
            driver.get("https://www.example.com");

            // Your test code here...

            System.out.println("Implicit wait of 10 seconds has been set.");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}