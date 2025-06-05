# Section 02: Explicit Waits and ExpectedConditions

## Overview

Explicit waits are targeted, condition-specific waits that work at exactly the specified location in your code. Unlike implicit waits, explicit waits allow you to wait for specific conditions to be met before proceeding, making them far more powerful and flexible for modern web applications.

## Key Concepts

### What is an Explicit Wait?

An explicit wait is code that waits for a specific condition to be true before proceeding with the next step. It uses `WebDriverWait` class combined with `ExpectedConditions` to wait for various states and conditions.

### Important Characteristics

- **Targeted**: Works exactly where you place it in code
- **Condition-Specific**: Waits for specific states, not just element presence
- **Flexible**: Can wait for complex conditions and combinations
- **Intelligent**: Polls frequently (default: every 500ms) until condition is met
- **Timeout-Based**: Has a maximum wait time to prevent infinite loops

## Code Examples Analysis

### Example 1: Basic Explicit Wait (`Ex01BasicExplicitWait.java`)

```java
// Create WebDriverWait instance
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Wait for element to be visible (not just present)
By locator = By.id("exampleId");
wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
```

**What this does:**
- Creates a wait that will check for up to 10 seconds
- Waits for element to be both present in DOM AND visible to user
- Polls every 500ms (default) until condition is met
- Throws `TimeoutException` if condition not met within timeout

### Example 2: Different ExpectedConditions (`Ex02DifferentExpectedConditions.java`)

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Wait for specific page title
wait.until(ExpectedConditions.titleIs("Example Domain"));

// Wait for element to be clickable (visible + enabled)
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

// Wait for specific text to appear in element
wait.until(ExpectedConditions.textToBePresentInElementLocated(paragraphLocator, "specific text"));
```

**Different conditions demonstrated:**
1. **titleIs()**: Page title matches exactly
2. **elementToBeClickable()**: Element is visible, enabled, and clickable
3. **textToBePresentInElementLocated()**: Specific text appears in element

### Example 3: Custom Polling Interval (`Ex03CustomPollingInterval.java`)

```java
// Custom polling interval: check every 300ms instead of default 500ms
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(300));
```

**Benefits of custom polling:**
- **Faster Detection**: More frequent checks for rapidly changing elements
- **Resource Control**: Less frequent checks to reduce system load
- **Fine-tuning**: Optimize for specific application behavior

### Example 4: Custom ExpectedCondition (`Ex04CustomExpectedCondition.java`)

```java
ExpectedCondition<Boolean> customCondition = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) {
        WebElement element = driver.findElement(By.tagName("p"));
        return element.getText().length() > 50;
    }
};

wait.until(customCondition);
```

**Custom condition capabilities:**
- Wait for text length to exceed certain value
- Wait for specific calculations or business logic
- Combine multiple element states
- Custom return types (Boolean, WebElement, List, etc.)

### Example 5: Combining Multiple Conditions (`Ex05CombiningConditions.java`)

```java
wait.until(ExpectedConditions.and(
    ExpectedConditions.titleIs("Example Domain"),
    ExpectedConditions.presenceOfElementLocated(By.tagName("p")),
    ExpectedConditions.urlContains("example.com")
));
```

**Logical operators:**
- **and()**: ALL conditions must be true
- **or()**: ANY condition must be true
- **not()**: Condition must be false

## Complete ExpectedConditions Reference

### Element Presence and Visibility
```java
// Element exists in DOM (may not be visible)
ExpectedConditions.presenceOfElementLocated(By locator)

// Element is visible (displayed with height/width > 0)
ExpectedConditions.visibilityOfElementLocated(By locator)
ExpectedConditions.visibilityOf(WebElement element)

// All specified elements are visible
ExpectedConditions.visibilityOfAllElementsLocatedBy(By locator)
```

### Element Interaction States
```java
// Element is clickable (visible + enabled)
ExpectedConditions.elementToBeClickable(By locator)
ExpectedConditions.elementToBeClickable(WebElement element)

// Element selection state
ExpectedConditions.elementToBeSelected(WebElement element)
ExpectedConditions.elementSelectionStateToBe(WebElement element, boolean selected)
```

### Element Disappearance
```java
// Element becomes stale (removed from DOM)
ExpectedConditions.stalenessOf(WebElement element)

// Element becomes invisible
ExpectedConditions.invisibilityOfElementLocated(By locator)
ExpectedConditions.invisibilityOfElementWithText(By locator, String text)
```

### Text and Attributes
```java
// Text appears in element
ExpectedConditions.textToBePresentInElement(WebElement element, String text)
ExpectedConditions.textToBePresentInElementLocated(By locator, String text)

// Text appears in element's value attribute
ExpectedConditions.textToBePresentInElementValue(By locator, String text)

// Attribute contains specific value
ExpectedConditions.attributeToBe(By locator, String attribute, String value)
ExpectedConditions.attributeContains(WebElement element, String attribute, String value)
```

### Page States
```java
// Page title conditions
ExpectedConditions.titleIs(String title)
ExpectedConditions.titleContains(String title)

// URL conditions
ExpectedConditions.urlToBe(String url)
ExpectedConditions.urlContains(String fraction)
ExpectedConditions.urlMatches(String regex)
```

### Alerts and Frames
```java
// Alert is present
ExpectedConditions.alertIsPresent()

// Frame is available and switch to it
ExpectedConditions.frameToBeAvailableAndSwitchToIt(By locator)
ExpectedConditions.frameToBeAvailableAndSwitchToIt(String nameOrId)
```

### Collections
```java
// Number of elements
ExpectedConditions.numberOfElementsToBe(By locator, Integer number)
ExpectedConditions.numberOfElementsToBeMoreThan(By locator, Integer number)
ExpectedConditions.numberOfElementsToBeLessThan(By locator, Integer number)

// All elements are present
ExpectedConditions.presenceOfAllElementsLocatedBy(By locator)
```

## Pros and Cons

### ✅ Advantages

1. **Precision**: Wait for exactly what you need
2. **Condition-Specific**: Many built-in conditions for common scenarios
3. **Flexible**: Can create custom conditions for any scenario
4. **Efficient**: Stops immediately when condition is met
5. **Powerful**: Can combine multiple conditions with logical operators
6. **Localized**: Only affects specific points in your code
7. **Clear Intent**: Code clearly shows what you're waiting for
8. **No Global Side Effects**: Doesn't interfere with other operations

### ❌ Disadvantages

1. **More Code**: Requires explicit coding at each wait point
2. **Repetition**: Similar waits may be repeated across tests
3. **Complexity**: Can become complex with custom conditions
4. **Learning Curve**: Requires knowledge of various ExpectedConditions
5. **Maintenance**: More places in code that might need updates

## When Explicit Waits Fall Short

### 1. Simple Element Location (Overkill)
```java
// OVERKILL for simple, always-present elements
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title")));
// Title tag is always present immediately
```

### 2. Complex Custom Logic
```java
// COMPLEX: Waiting for multiple unrelated conditions
ExpectedCondition<Boolean> complexCondition = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) {
        // 50 lines of complex business logic
        // Multiple database checks
        // Complex calculations
        // This becomes hard to maintain and debug
    }
};
```

### 3. Performance-Critical Scenarios
```java
// INEFFICIENT: In tight loops or performance-critical code
for (int i = 0; i < 1000; i++) {
    wait.until(ExpectedConditions.elementToBeClickable(By.id("button" + i)));
    // Creating new waits repeatedly can be expensive
}
```

## Best Practices

### ✅ Effective Use Patterns

1. **Dynamic Content Loading**
```java
// Wait for AJAX content to load
wait.until(ExpectedConditions.visibilityOfElementLocated(By.class("dynamic-content")));
```

2. **Form Interactions**
```java
// Wait for form elements to become interactive
WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
submitButton.click();
```

3. **Page Transitions**
```java
// Wait for new page to load with specific title
wait.until(ExpectedConditions.titleContains("New Page"));
```

4. **State Changes**
```java
// Wait for element text to change
wait.until(ExpectedConditions.textToBePresentInElement(statusElement, "Complete"));
```

### Recommended Timeout Values

```java
// Short waits for fast operations (AJAX calls)
WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

// Medium waits for typical page operations
WebDriverWait mediumWait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Long waits for slow operations (file uploads, complex processing)
WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
```

### Creating Reusable Wait Methods

```java
public class WaitUtils {
    private WebDriverWait wait;
    
    public WaitUtils(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public boolean waitForTextChange(WebElement element, String expectedText) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
    }
}
```

## Common Pitfalls and Solutions

### 1. Mixing with Implicit Waits
```java
// PROBLEMATIC
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

// SOLUTION: Use one or the other, not both
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // Disable implicit
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Use explicit
```

### 2. Ignoring Return Values
```java
// PROBLEMATIC: Not using returned element
wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
driver.findElement(By.id("submit")).click(); // Searches again!

// SOLUTION: Use returned element
WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
submitButton.click();
```

### 3. Using Wrong Condition
```java
// PROBLEMATIC: Waiting for presence when you need visibility
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button")));
driver.findElement(By.id("button")).click(); // Might fail if not visible!

// SOLUTION: Use appropriate condition
wait.until(ExpectedConditions.elementToBeClickable(By.id("button"))).click();
```

## Teaching Summary

Explicit waits are like having **targeted patience** for specific situations in your tests. They're the preferred choice for modern web applications because they:

1. **Solve Real Problems**: Handle the dynamic nature of modern web apps
2. **Provide Precision**: Wait for exactly what you need
3. **Offer Flexibility**: Extensive library of conditions plus custom options
4. **Improve Reliability**: Reduce flaky tests caused by timing issues
5. **Express Intent Clearly**: Make test logic obvious to readers

**Key Learning Points:**
1. Explicit waits are condition-specific and localized
2. ExpectedConditions provides solutions for most common scenarios
3. Custom conditions handle unique business logic
4. Proper condition selection is crucial for effectiveness
5. They're essential for reliable automation of dynamic applications

The investment in learning explicit waits pays off significantly in test reliability and maintainability, especially as applications become more dynamic and interactive.