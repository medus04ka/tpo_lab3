import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class RegistrationModal(driver: WebDriver) : Page(driver) {

    companion object {
        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Регистрация')]"
        )

        private val EMAIL_INPUT = By.xpath(
            "//*[contains(@class,'hyv-cmn-input') and .//label[normalize-space()='Электронная почта']]//input"
        )

        private val CODE_INPUT = By.xpath(
            "//*[contains(@class,'hyv-cmn-input') and .//label[normalize-space()='Код подтверждения']]//input"
        )

        private val SEND_CODE_BUTTON = By.xpath(
            "//*[contains(@class,'hyv-cmn-input') and .//label[normalize-space()='Код подтверждения']]" +
                    "//span[normalize-space()='Отправить']/ancestor::a"
        )

        private val PASSWORD_INPUT = By.xpath(
            "//*[contains(@class,'hyv-cmn-input') and .//label[normalize-space()='Введите пароль']]//input"
        )

        private val CONFIRM_PASSWORD_INPUT = By.xpath(
            "//*[contains(@class,'hyv-cmn-input') and .//label[normalize-space()='Пожалуйста, введите пароль ещё раз']]//input"
        )

        private val AGREEMENT_CHECKBOX_INNER = By.xpath(
            "//*[contains(@class,'agreement_privacyAndUser')]//span[contains(@class,'el-checkbox__inner')]"
        )

        private val REGISTER_BUTTON_ENABLED = By.xpath(
            "//button[.//span[normalize-space()='Регистрация'] and not(contains(@class,'is-disabled')) and not(@disabled)]"
        )
    }

    fun waitUntilVisible(): RegistrationModal {
        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            pageHasRegistrationText() ||
                    existsPresent(TITLE) ||
                    existsPresent(EMAIL_INPUT) ||
                    existsPresent(CODE_INPUT)
        }

        return this
    }

    fun switchToRegistrationFrameIfNeeded(): RegistrationModal {
        driver.switchTo().defaultContent()

        val frames = driver.findElements(By.tagName("iframe"))

        for (frame in frames) {
            try {
                driver.switchTo().defaultContent()
                driver.switchTo().frame(frame)

                if (
                    existsPresent(TITLE) ||
                    existsPresent(EMAIL_INPUT) ||
                    existsPresent(CODE_INPUT) ||
                    pageHasRegistrationText()
                ) {
                    return this
                }
            } catch (e: Exception) {
                driver.switchTo().defaultContent()
            }
        }

        driver.switchTo().defaultContent()
        return this
    }

    fun isVisible(): Boolean {
        return try {
            waitUntilVisible()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun typeEmail(email: String): RegistrationModal {
        waitUntilVisible()

        val emailInput = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            driver.findElements(EMAIL_INPUT)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        emailInput.clear()
        emailInput.sendKeys(email)

        return this
    }

    fun clickSendCode(): RegistrationModal {
        waitUntilVisible()

        val sendCodeButton = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            driver.findElements(SEND_CODE_BUTTON)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        clickElement(sendCodeButton)

        return this
    }

    fun waitForManualCode(waitMillis: Long = 60_000): RegistrationModal {
        Thread.sleep(waitMillis)

        return this
    }

    fun typePassword(password: String): RegistrationModal {
        waitUntilVisible()

        val passwordInput = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            driver.findElements(PASSWORD_INPUT)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        val confirmPasswordInput = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            driver.findElements(CONFIRM_PASSWORD_INPUT)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        passwordInput.clear()
        passwordInput.sendKeys(password)

        confirmPasswordInput.clear()
        confirmPasswordInput.sendKeys(password)

        return this
    }

    fun acceptAgreement(): RegistrationModal {
        waitUntilVisible()

        val checkbox = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            driver.findElements(AGREEMENT_CHECKBOX_INNER)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        clickElement(checkbox)

        return this
    }

    fun clickRegisterButton(): RegistrationModal {
        waitUntilVisible()

        val registerButton = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToRegistrationFrameIfNeeded()

            driver.findElements(REGISTER_BUTTON_ENABLED)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        clickElement(registerButton)

        return this
    }

    fun registerWithManualCode(email: String, password: String): RegistrationModal {
        typeEmail(email)

        clickSendCode()

        waitForManualCode(60_000)

        typePassword(password)

        acceptAgreement()

        clickRegisterButton()

        return this
    }

    private fun clickElement(element: org.openqa.selenium.WebElement) {
        try {
            element.click()
        } catch (e: Exception) {
            (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
        }
    }

    private fun pageHasRegistrationText(): Boolean =
        driver.pageSource.contains("Регистрация", ignoreCase = true) ||
                driver.pageSource.contains("Электронная почта", ignoreCase = true) ||
                driver.pageSource.contains("Код подтверждения", ignoreCase = true) ||
                driver.pageSource.contains("Введите пароль", ignoreCase = true)

    private fun existsPresent(selector: By): Boolean {
        return try {
            driver.findElements(selector).isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}