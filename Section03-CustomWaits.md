# Section 03: Custom Waits - Beyond Standard ExpectedConditions

## Overview

Custom waits are user-defined waiting mechanisms that go beyond the standard `ExpectedConditions`. They allow you to implement specific business logic, complex conditions, and scenarios that aren't covered by built-in conditions. This section covers various approaches from simple lambda expressions to sophisticated custom wait implementations.

## Key Concepts

### What are Custom Waits?

Custom waits are implementations of waiting logic tailored to specific application needs. They can be:
- **Lambda Expressions**: Simple, inline custom conditions
- **ExpectedCondition Implementations**: Reusable custom condition classes
- **FluentWait Configurations**: Highly configurable wait mechanisms
- **Completely Custom Solutions**: Built from scratch with custom logic and exception handling

### When to Use Custom Waits

- Standard `ExpectedConditions` don't cover your use case
- Need to wait for complex business logic conditions
- Require specific polling intervals or timeout behavior
- Want to integrate with custom exception handling
- Need to wait for multiple unrelated conditions simultaneously

## Code Examples Analysis

### Example 1: Basic Custom Wait (`Ex01BasicCustomWait.java`)

```java
ExpectedCondition<Boolean> customCondition = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) {
        return driver.findElements(newsLinksLocator).size() == 10;
    }
};

new WebDriverWait(driver, Duration.ofSeconds(5)).until(customCondition);
```

**What this demonstrates:**
- Creating custom `ExpectedCondition` by implementing the interface
- Waiting for a specific count of elements (not available in standard conditions)
- Returning Boolean to indicate condition success/failure
- Anonymous inner class approach for one-off conditions

### Example 2: Lambda Custom Wait (`Ex02LambdaCustomWait.java`)

```java
new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
    d.findElements(newsLinksLocator).size() == 10
);
```

**Advantages of lambda approach:**
- **Concise**: Much shorter than anonymous inner class
- **Readable**: Clear and direct expression of the condition
- **Modern**: Uses Java 8+ functional programming features
- **Inline**: Perfect for simple, one-time-use conditions

### Example 3: Element Collection Wait (`Ex03ElementCollectionWait.java`)

```java
@Test
public void elementCollectionTest() {
    By newsLinksLocator = By.cssSelector(".tabs-content__item");
    
    // Wait for specific number of elements
    new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
        d.findElements(newsLinksLocator).size() == 10
    );
    
    // Verify elements after waiting
    List<WebElement> newsLinks = driver.findElements(newsLinksLocator);
    Assert.assertEquals(newsLinks.size(), 10);
    
    newsLinks.forEach(link -> {
        Assert.assertTrue(link.isDisplayed());
        Assert.assertTrue(link.getText().contains(" "));
    });
}
```

**Real-world testing pattern:**
- Wait for dynamic content to fully load
- Verify the count matches expectations
- Perform additional validations on collected elements
- Demonstrates TestNG integration

### Example 4: FluentWait Custom Implementation (`Ex04FluentWaitCustom.java`)

```java
Wait<WebDriver> wait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(30))      // Maximum wait time
    .pollingEvery(Duration.ofSeconds(5))      // Check every 5 seconds
    .ignoring(NoSuchElementException.class);  // Ignore specific exceptions

WebElement element = wait.until(d -> {
    WebElement el = d.findElement(By.id("dynamicElement"));
    return el.isDisplayed() ? el : null;     // Return element or null
});
```

**FluentWait advantages:**
- **Custom Polling**: Control how often conditions are checked
- **Exception Handling**: Ignore specific exceptions during polling
- **Flexible Timeout**: Different timeout from default WebDriverWait
- **Return Types**: Can return elements, lists, or custom objects

### Example 5: Custom Wait with Timeout and Exception Handling (`Ex05CustomWaitWithTimeout.java`)

```java
private static WebElement waitForElement(WebDriver driver, By locator, Duration timeout) 
        throws CustomTimeoutException {
    long end = System.currentTimeMillis() + timeout.toMillis();
    
    while (System.currentTimeMillis() < end) {
        try {
            WebElement element = driver.findElement(locator);
            if (element != null && element.isDisplayed()) {
                return element;
            }
        } catch (NoSuchElementException e) {
            // Continue polling
        }
        
        try {
            Thread.sleep(500);  // Wait before next attempt
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CustomTimeoutException("Wait interrupted", e);
        }
    }
    
    throw new CustomTimeoutException("Element not found within timeout");
}
```

**Custom implementation features:**
- **Manual Polling Loop**: Complete control over polling mechanism
- **Custom Exceptions**: Domain-specific exception types
- **Flexible Logic**: Can implement any waiting strategy
- **Performance Control**: Custom sleep intervals

## Types of Custom Waits

### 1. Lambda-Based Waits (Simplest)

```java
// Wait for specific text length
wait.until(d -> d.findElement(By.id("content")).getText().length() > 100);

// Wait for attribute value
wait.until(d -> "complete".equals(d.findElement(By.id("status")).getAttribute("data-state")));

// Wait for multiple elements with condition
wait.until(d -> d.findElements(By.className("item")).stream()
    .allMatch(el -> el.getAttribute("class").contains("loaded")));
```

### 2. Reusable ExpectedCondition Classes

```java
public class CustomConditions {
    
    public static ExpectedCondition<Boolean> elementTextLengthToBe(By locator, int expectedLength) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    WebElement element = driver.findElement(locator);
                    return element.getText().length() == expectedLength;
                } catch (NoSuchElementException e) {
                    return false;
                }
            }
            
            @Override
            public String toString() {
                return String.format("text length of element located by %s to be %d", locator, expectedLength);
            }
        };
    }
    
    public static ExpectedCondition<List<WebElement>> elementsToBeClickable(By locator) {
        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                if (elements.isEmpty()) return null;
                
                for (WebElement element : elements) {
                    if (!element.isEnabled() || !element.isDisplayed()) {
                        return null;
                    }
                }
                return elements;
            }
        };
    }
}
```

### 3. Business Logic Waits

```java
public class BusinessWaits {
    
    public static ExpectedCondition<Boolean> orderStatusToBe(String orderId, OrderStatus expectedStatus) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // Navigate to order status page
                driver.get("/orders/" + orderId);
                
                // Check status element
                WebElement statusElement = driver.findElement(By.className("order-status"));
                String currentStatus = statusElement.getText().trim();
                
                return expectedStatus.toString().equalsIgnoreCase(currentStatus);
            }
        };
    }
    
    public static ExpectedCondition<Boolean> formValidationToComplete() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // Check if all required field validations are complete
                List<WebElement> requiredFields = driver.findElements(By.cssSelector("[required]"));
                List<WebElement> errorMessages = driver.findElements(By.className("error-message"));
                
                // All required fields should have values and no error messages should be visible
                boolean allFieldsFilled = requiredFields.stream()
                    .allMatch(field -> !field.getAttribute("value").isEmpty());
                
                boolean noErrors = errorMessages.stream()
                    .noneMatch(WebElement::isDisplayed);
                
                return allFieldsFilled && noErrors;
            }
        };
    }
}
```

### 4. Performance-Optimized Waits

```java
public class PerformanceWaits {
    
    // Wait with reduced polling frequency for slow operations
    public static WebElement waitForSlowElement(WebDriver driver, By locator, Duration timeout) {
        Wait<WebDriver> slowWait = new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(Duration.ofSeconds(2))  // Check every 2 seconds
            .ignoring(NoSuchElementException.class);
            
        return slowWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    // Wait with increased polling frequency for fast operations
    public static WebElement waitForFastElement(WebDriver driver, By locator, Duration timeout) {
        Wait<WebDriver> fastWait = new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(Duration.ofMillis(100))  // Check every 100ms
            .ignoring(NoSuchElementException.class);
            
        return fastWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
```

## Pros and Cons

### ✅ Advantages

1. **Unlimited Flexibility**: Can implement any waiting logic imaginable
2. **Business Logic Integration**: Wait for domain-specific conditions
3. **Performance Optimization**: Custom polling intervals and strategies
4. **Complex Conditions**: Multiple criteria, calculations, external system checks
5. **Custom Return Types**: Return exactly what you need (elements, lists, custom objects)
6. **Exception Control**: Custom exception handling and error messages
7. **Reusability**: Create libraries of common custom conditions
8. **Integration**: Combine with external APIs, databases, or services

### ❌ Disadvantages

1. **Complexity**: More complex to implement and maintain
2. **Development Time**: Takes longer to create than using standard conditions
3. **Testing Required**: Custom code needs its own testing
4. **Debugging Difficulty**: Harder to debug custom logic
5. **Performance Considerations**: Inefficient implementations can slow tests
6. **Team Knowledge**: Requires team understanding of custom implementations
7. **Maintenance Overhead**: More code to maintain and update

## When Custom Waits Fall Short

### 1. Over-Engineering Simple Cases
```java
// OVERKILL: Custom wait for simple element visibility
ExpectedCondition<WebElement> customVisible = new ExpectedCondition<WebElement>() {
    @Override
    public WebElement apply(WebDriver driver) {
        WebElement element = driver.findElement(By.id("simple"));
        return element.isDisplayed() ? element : null;
    }
};

// BETTER: Use standard condition
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("simple")));
```

### 2. Performance-Critical Scenarios
```java
// PROBLEMATIC: Complex custom condition in tight loop
for (int i = 0; i < 1000; i++) {
    wait.until(new ComplexBusinessLogicCondition()); // Expensive for each iteration
}

// BETTER: Cache results or use simpler conditions
```

### 3. Maintenance Nightmares
```java
// PROBLEMATIC: Overly complex custom condition
ExpectedCondition<Boolean> overlyComplex = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) {
        // 100+ lines of complex logic
        // Multiple try-catch blocks
        // Complex calculations
        // Database calls
        // API calls
        // This becomes unmaintainable
    }
};
```

## Best Practices

### ✅ Design Guidelines

1. **Start Simple**: Begin with lambda expressions for simple conditions
2. **Reuse Common Patterns**: Create utility classes for frequently used conditions
3. **Clear Naming**: Use descriptive names for custom conditions
4. **Document Purpose**: Comment complex custom logic thoroughly
5. **Handle Exceptions**: Gracefully handle exceptions during polling
6. **Provide Meaningful toString()**: Help with debugging and error messages

### ✅ Implementation Patterns

```java
public class WaitUtils {
    private final WebDriverWait standardWait;
    private final Wait<WebDriver> slowWait;
    private final Wait<WebDriver> fastWait;
    
    public WaitUtils(WebDriver driver) {
        this.standardWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.slowWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(60))
            .pollingEvery(Duration.ofSeconds(5));
        this.fastWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(5))
            .pollingEvery(Duration.ofMillis(100));
    }
    
    // Simple lambda conditions
    public boolean waitForElementCount(By locator, int expectedCount) {
        return standardWait.until(d -> d.findElements(locator).size() == expectedCount);
    }
    
    // Reusable custom conditions
    public WebElement waitForElementWithText(By locator, String expectedText) {
        return standardWait.until(elementToHaveText(locator, expectedText));
    }
    
    // Custom condition factory method
    private ExpectedCondition<WebElement> elementToHaveText(By locator, String text) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = driver.findElement(locator);
                return text.equals(element.getText()) ? element : null;
            }
            
            @Override
            public String toString() {
                return String.format("element located by %s to have text '%s'", locator, text);
            }
        };
    }
}
```

### ✅ Error Handling

```java
public static ExpectedCondition<Boolean> safeCustomCondition(By locator, Predicate<WebElement> condition) {
    return new ExpectedCondition<Boolean>() {
        @Override
        public Boolean apply(WebDriver driver) {
            try {
                WebElement element = driver.findElement(locator);
                return condition.test(element);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return false; // Continue polling
            } catch (Exception e) {
                // Log unexpected exceptions but continue
                System.err.println("Unexpected exception in custom wait: " + e.getMessage());
                return false;
            }
        }
        
        @Override
        public String toString() {
            return String.format("custom condition for element %s", locator);
        }
    };
}
```

## Common Pitfalls and Solutions

### 1. Exception Handling
```java
// PROBLEMATIC: Not handling exceptions
wait.until(d -> d.findElement(By.id("dynamic")).getText().equals("loaded"));
// Throws exception if element not found, stopping the wait

// SOLUTION: Handle exceptions properly
wait.until(d -> {
    try {
        return d.findElement(By.id("dynamic")).getText().equals("loaded");
    } catch (NoSuchElementException e) {
        return false; // Continue polling
    }
});
```

### 2. Inefficient Polling
```java
// PROBLEMATIC: Expensive operations in polling
wait.until(d -> {
    // Database call
    String dbValue = database.getStatus(orderId);
    // API call
    String apiValue = apiClient.getStatus(orderId);
    // Complex calculation
    return complexCalculation(dbValue, apiValue);
});

// SOLUTION: Cache or optimize expensive operations
wait.until(d -> {
    // Use cached values or more efficient checks
    WebElement statusElement = d.findElement(By.id("status"));
    return "complete".equals(statusElement.getAttribute("data-status"));
});
```

### 3. Missing toString() Implementation
```java
// PROBLEMATIC: Poor error messages
ExpectedCondition<Boolean> condition = d -> /* complex logic */;
// Error: "Timed out after 10 seconds waiting for java.util.function.Function$Lambda$123"

// SOLUTION: Implement meaningful toString()
ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) { /* logic */ }
    
    @Override
    public String toString() {
        return "order status to be 'completed' and payment to be 'processed'";
    }
};
```

## Teaching Summary

Custom waits represent the **pinnacle of waiting strategy flexibility** in Selenium. They bridge the gap between standard conditions and complex real-world requirements. Key learning outcomes:

**Progressive Complexity:**
1. **Lambda Expressions**: Start here for simple custom conditions
2. **ExpectedCondition Classes**: Use for reusable, complex conditions  
3. **FluentWait**: When you need custom polling or exception handling
4. **Fully Custom**: For unique requirements not covered by WebDriverWait

**When to Use Each Approach:**
- **Simple Logic**: Lambda expressions (`wait.until(d -> condition)`)
- **Reusable Logic**: Custom ExpectedCondition classes
- **Complex Polling**: FluentWait with custom intervals
- **Integration Needs**: Fully custom implementations

**Critical Success Factors:**
1. **Exception Handling**: Always handle potential exceptions gracefully
2. **Performance**: Be mindful of expensive operations in polling loops
3. **Maintainability**: Keep custom conditions simple and well-documented
4. **Reusability**: Create utility classes for common patterns
5. **Debugging**: Implement meaningful toString() methods

Custom waits are powerful tools that should be used judiciously. They solve real problems but come with increased complexity. The key is knowing when the additional complexity is justified by the specific requirements of your application.