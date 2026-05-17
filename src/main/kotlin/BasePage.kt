import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

open class BasePage(
    protected val driver: WebDriver,
    protected val wait: WebDriverWait,
    protected val actions: Actions
) {
    fun currentUrl(): String = driver.currentUrl
    fun title(): String = driver.title
    fun pageSource(): String = driver.pageSource

    protected fun findVisible(by: By): WebElement =
        wait.until(ExpectedConditions.visibilityOfElementLocated(by))

    protected fun findPresent(by: By): WebElement =
        wait.until(ExpectedConditions.presenceOfElementLocated(by))

    protected fun findClickable(by: By): WebElement =
        wait.until(ExpectedConditions.elementToBeClickable(by))

    protected fun findAll(by: By): List<WebElement> =
        driver.findElements(by)

    protected fun waitVisibleLong(by: By, seconds: Long = 80): WebElement =
        WebDriverWait(driver, Duration.ofSeconds(seconds))
            .until(ExpectedConditions.visibilityOfElementLocated(by))

    protected fun waitClickableLong(by: By, seconds: Long = 80): WebElement =
        WebDriverWait(driver, Duration.ofSeconds(seconds))
            .until(ExpectedConditions.elementToBeClickable(by))

    protected fun existsVisible(by: By, seconds: Long = 5): Boolean {
        return try {
            WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by))
            true
        } catch (e: TimeoutException) {
            false
        }
    }

    protected fun scrollIntoView(element: WebElement) {
        (driver as JavascriptExecutor).executeScript(
            "arguments[0].scrollIntoView({block:'center', inline:'center'});",
            element
        )
    }

    protected fun jsClick(element: WebElement) {
        (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
    }

    protected fun clickSafely(by: By): WebElement {
        val element = findClickable(by)
        scrollIntoView(element)

        try {
            element.click()
        } catch (e: Exception) {
            jsClick(element)
        }

        return element
    }

    protected fun clickSafely(element: WebElement): WebElement {
        scrollIntoView(element)

        try {
            element.click()
        } catch (e: Exception) {
            jsClick(element)
        }

        return element
    }

    protected fun waitPageReady() {
        wait.until {
            (driver as JavascriptExecutor)
                .executeScript("return document.readyState") == "complete"
        }
    }

    protected fun waitUrlContains(value: String) {
        wait.until(ExpectedConditions.urlContains(value))
    }

    protected fun xpathText(text: String): String =
        "//*[contains(normalize-space(), '$text')]"

    protected fun xpathExactText(text: String): String =
        "//*[normalize-space()='$text']"
}