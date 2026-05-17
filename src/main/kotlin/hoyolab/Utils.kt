package hoyolab

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

    private fun getChromeDriver(): WebDriver {
        val options = ChromeOptions()
        options.addArguments("--start-maximized")
        options.addArguments("--lang=ru-RU")
        return ChromeDriver(options)
    }

    private fun getFirefoxDriver(): WebDriver {
        val options = FirefoxOptions()
        options.addPreference("intl.accept_languages", "ru-RU, ru")
        return FirefoxDriver(options)
    }

    fun getElementBySelector(driver: WebDriver, selector: By, timeout: Long = 60): WebElement {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector))
    }

    fun getClickableElement(driver: WebDriver, selector: By, timeout: Long = 60): WebElement {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
        return wait.until(ExpectedConditions.elementToBeClickable(selector))
    }

    fun waitUntilPageLoads(driver: WebDriver, timeout: Long = 60) {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
        wait.until {
            (it as JavascriptExecutor)
                .executeScript("return document.readyState") == "complete"
        }
    }

    fun click(driver: WebDriver, selector: By) {
        val element = getClickableElement(driver, selector)
        try {
            element.click()
        } catch (e: Exception) {
            (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
        }
    }

    fun exists(driver: WebDriver, selector: By, timeout: Long = 5): Boolean {
        return try {
            getElementBySelector(driver, selector, timeout)
            true
        } catch (e: Exception) {
            false
        }
    }
}