# Lecture 24: Locators, Waiters, Expected Conditions

## Waiters
Waiters are an essential attribute of any UI tests for dynamic applications. Scripts run much faster than the application's response to commands, so often in scripts, you need to wait for a certain state of the application for further interaction with it.

### Types of Waiters:

#### 1. **Implicit Waits**
Implicit Waits configure the WebDriver instance to repeatedly try to find an element (or elements) on the page within a specified period if the element is not found immediately. Only after this time has elapsed will the WebDriver throw an ElementNotFoundException.

Implicit waits instruct the WebDriver to poll the DOM for a certain period when trying to find elements if they are not immediately present. By default, this value is set to 0. Once set, it will be used before executing each findElement command, waiting for the element, throughout the WebDriver instance's life or until changed.

However, implicit waits do not allow you to wait for events such as the disappearance of an element from the DOM or the change of an element's properties. For such events, explicit waits should be used.

```java
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
```

Implicit Wait can be used for:
- Waiting for the page to fully load: `pageLoadTimeout()`
- Waiting for an element to appear on the page: `implicitlyWait()`
- Waiting for an asynchronous request to complete: `setScriptTimeout()`

Setting implicit waits usage would look like this:

```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS); // deprecated
```

#### 2. **Explicit Waits**
Explicit Waits are code that waits for a specific event before proceeding with script commands. This wait works exactly at the specified location. To organize explicit waits, Selenium provides the `WebDriverWait` class and the `ExpectedCondition` interface.

Important things to remember about explicit waits:
- The wait will work exactly where specified.
- Like implicit waits, it needs a time limit.
- It waits for the necessary condition to be met.
- It waits for Ajax requests to complete.

Explicit waits can be used through `WebDriverWait`. Initialization looks like this:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
```

Here, `driver` is a reference to our WebDriver instance, and the number 10 is the timeout in seconds. In the test, the wait itself will look something like this:

```java
wait.until(ExpectedConditions.visibilityOfElementLocated(inboxLocator));
```

Or:

```java
new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(ExpectedConditions.visibilityOfElementLocated(inboxLocator));
```

By default, `WebDriverWait` calls the `ExpectedCondition` every 500 milliseconds until the condition is met. This value can be changed by setting the `pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS)` property or using the `WebDriverWait(WebDriver driver, long timeOutInSeconds, long sleepInMillis)` constructor, which takes the polling duration in milliseconds as the third parameter. By default, `WebDriverWait` ignores `NotFoundException` that occurs when calling the condition.

```java
new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(300))
        .until(ExpectedConditions.visibilityOfElementLocated(inboxLocator));
```

The worst option is to use `Thread.sleep(1000)`, in which case the script will simply wait for a certain amount of time. This does not guarantee the desired event will occur or will be too excessive and increase the test execution time. It is preferable to use `WebDriverWait` and `ExpectedCondition`.

The `ExpectedConditions` class provides ready-made waiting conditions. Some of them are:
- `stalenessOf(WebElement element)` — waits for the element to disappear from the DOM.
- `titleIs(java.lang.String title)` — waits for the page title.
- `textToBePresentInElement(By locator, java.lang.String text)` — waits for the specified text in the element.
- `visibilityOfElementLocated(By locator)` — waits for the element to be present in the DOM and visible.

Here is the complete list of what you can wait for with `ExpectedConditions`:
- `alertIsPresent()`
- `elementSelectionStateToBe()`
- `elementToBeClickable()`
- `elementToBeSelected()`
- `frameToBeAvailableAndSwitchToIt()`
- `invisibilityOfTheElementLocated()`
- `invisibilityOfElementWithText()`
- `presenceOfAllElementsLocatedBy()`
- `presenceOfElementLocated()`
- `textToBePresentInElement()`
- `textToBePresentInElementLocated()`
- `textToBePresentInElementValue()`
- `titleIs()`
- `titleContains()`
- `visibilityOf()`
- `visibilityOfAllElements()`
- `visibilityOfAllElementsLocatedBy()`
- `visibilityOfElementLocated()`

### Writing Custom Waits
Selenium waits do not always meet the tester's needs. In such cases, we can write methods that prevent auto-tests from failing. Custom waits will work the same way as explicit waits, i.e., trigger in the specified part of the test, and the condition to be met needs to override the `apply()` method of the `ExpectedCondition` interface, which extends the `Function` functional interface.

Example using `ExpectedCondition`:

```java
By newsLinksLocator = By.cssSelector(".tabs-content__item");
new WebDriverWait(driver, 5)
        .until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.findElements(newsLinksLocator).size() == 10;
            }
        });
```

Or simplify using a lambda:

```java
new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> driver
        .findElements(newsLinksLocator).size() == 10);
```

Test for a collection of elements with the condition to wait for visibility of all 10 locators:

```java
@Test
public void elementCollectionTest() {
    By newsLinksLocator = By.cssSelector(".tabs-content__item");
    new WebDriverWait(driver, 5).until(driver -> driver
            .findElements(newsLinksLocator).size() == 10);
    List<WebElement> newsLinks = driver.findElements(newsLinksLocator);
    Assert.assertEquals(newsLinks.size(), 10);
    newsLinks.forEach(link -> {
        Assert.assertTrue(link.isDisplayed());
        Assert.assertTrue(link.getText().contains(" "));
    });
}
```

Run the test.

### Summary
We have studied implicit and explicit waits, learned about the `ExpectedConditions` class and its methods, and found that we can write custom waits by overriding the `apply()` method of the `ExpectedCondition` interface.