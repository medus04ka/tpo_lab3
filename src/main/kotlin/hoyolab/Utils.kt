package hoyolab

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

object Utils {

    const val BASE_URL = "https://act.hoyolab.com/sr/app/interactive-map/"

    fun getDrivers(): List<WebDriver> {
        return when (System.getProperty("browser", "chrome").lowercase()) {
            "chrome" -> listOf(getChromeDriver())
            "firefox" -> listOf(getFirefoxDriver())
            "both" -> listOf(getChromeDriver(), getFirefoxDriver())
            else -> listOf(getChromeDriver())
        }
    }

    fun getDriver(): WebDriver {
        return when (System.getProperty("browser", "chrome").lowercase()) {
            "chrome" -> getChromeDriver()
            "firefox" -> getFirefoxDriver()
            else -> getChromeDriver()
        }
    }

    private fun getChromeDriver(): WebDriver {
        WebDriverManager.chromedriver().setup()

        val options = ChromeOptions()
        options.addArguments("--start-maximized")
        options.addArguments("--lang=ru-RU")
        options.addArguments("--disable-blink-features=AutomationControlled")

        return ChromeDriver(options)
    }

    private fun getFirefoxDriver(): WebDriver {
        WebDriverManager.firefoxdriver().setup()

        val options = FirefoxOptions()
        options.addPreference("intl.accept_languages", "ru-RU, ru")

        return FirefoxDriver(options)
    }

    fun getElementBySelector(
        driver: WebDriver,
        selector: By,
        timeout: Long = 60
    ): WebElement {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector))
    }

    fun getPresentElement(
        driver: WebDriver,
        selector: By,
        timeout: Long = 60
    ): WebElement {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
        return wait.until(ExpectedConditions.presenceOfElementLocated(selector))
    }

    fun getClickableElement(
        driver: WebDriver,
        selector: By,
        timeout: Long = 60
    ): WebElement {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
        return wait.until(ExpectedConditions.elementToBeClickable(selector))
    }

    fun click(driver: WebDriver, selector: By, timeout: Long = 60) {
        val element = getClickableElement(driver, selector, timeout)
        scrollIntoView(driver, element)

        try {
            element.click()
        } catch (e: Exception) {
            jsClick(driver, element)
        }
    }

    fun jsClick(driver: WebDriver, element: WebElement) {
        (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
    }

    fun scrollIntoView(driver: WebDriver, element: WebElement) {
        (driver as JavascriptExecutor).executeScript(
            "arguments[0].scrollIntoView({block:'center', inline:'center'});",
            element
        )
    }

    fun exists(driver: WebDriver, selector: By, timeout: Long = 5): Boolean {
        return try {
            getElementBySelector(driver, selector, timeout)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun visible(driver: WebDriver, selector: By): Boolean {
        return driver.findElements(selector).any {
            try {
                it.isDisplayed
            } catch (e: Exception) {
                false
            }
        }
    }
}