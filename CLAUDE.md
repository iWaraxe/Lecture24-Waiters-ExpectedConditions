# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java educational project demonstrating Selenium WebDriver waiting strategies. It's organized as a lecture series (Lecture 24) covering three main types of waits: Implicit Waits, Explicit Waits, and Custom Waits.

## Build and Development Commands

```bash
# Compile the project
mvn compile

# Run a specific example class
mvn exec:java -Dexec.mainClass="com.coherentsolutions.java.webauto.section01.Ex01BasicImplicitWait"

# Clean and compile
mvn clean compile

# Package the project
mvn package
```

## Project Structure

The codebase is organized into three main sections:

- `section01/` - Implicit Wait examples and patterns
- `section02/` - Explicit Wait with ExpectedConditions 
- `section03/` - Custom Wait implementations

Each section contains basic examples and an `advanced/` subdirectory with more complex implementations. All examples follow a consistent pattern:
- WebDriver setup with ChromeDriver
- Demonstration of specific wait technique
- Proper resource cleanup in finally blocks

## Key Dependencies

- **Selenium WebDriver 4.22.0** - Main automation framework
- **WebDriverManager 5.9.1** - Browser driver management  
- **TestNG 7.10.2** - Testing framework (though examples use main methods)
- **Java 21** - Language version

## Code Patterns

Examples consistently use:
- ChromeDriver for browser automation
- Duration.ofSeconds() for timeout specifications
- Try-finally blocks for proper WebDriver cleanup
- Descriptive class names following Ex##PatternName convention
- Package structure reflecting wait type categories