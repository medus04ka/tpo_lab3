import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class AuthModal(driver: WebDriver) : Page(driver) {

    companion object {
        private const val TEST_LOGIN = "eeeeeeeeeeeeeeeeem33@gmail.com"
        private const val TEST_PASSWORD = "PisyaPopa232312"

        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Войти')]"
        )

        private val USERNAME_INPUT = By.xpath(
            "//input[@name='username' " +
                    "or @autocomplete='username' " +
                    "or contains(@placeholder, 'Имя пользователя') " +
                    "or contains(@placeholder, 'эл. почта') " +
                    "or contains(@placeholder, 'почта')]"
        )

        private val PASSWORD_INPUT = By.xpath(
            "//input[@name='password' " +
                    "or @type='password' " +
                    "or @autocomplete='current-password' " +
                    "or contains(@placeholder, 'Пароль')]"
        )

        private val LOGIN_BUTTON = By.xpath(
            "//button[.//*[contains(normalize-space(), 'Войти')] " +
                    "or contains(normalize-space(), 'Войти')]"
        )

        private val LOGIN_LOADING = By.xpath(
            "//*[contains(@class,'hyv-login-platform__loading')]"
        )

        private val REGISTER_LINK = By.xpath(
            "//span[normalize-space()='Зарегистрироваться сейчас']/ancestor::a"
        )

        private val HELP_LINK = By.xpath(
            "//span[normalize-space()='Столкнулись с проблемами?']/ancestor::a"
        )
    }

    fun waitUntilVisible(): AuthModal {
        WebDriverWait(driver, Duration.ofSeconds(10)).until {
            switchToLoginFrameIfNeeded()

            pageHasLoginText() ||
                    existsPresent(TITLE) ||
                    existsPresent(USERNAME_INPUT) ||
                    existsPresent(PASSWORD_INPUT)
        }

        return this
    }

    fun switchToLoginFrameIfNeeded(): AuthModal {
        driver.switchTo().defaultContent()

        val frames = driver.findElements(By.tagName("iframe"))

        for (frame in frames) {
            try {
                driver.switchTo().defaultContent()
                driver.switchTo().frame(frame)

                if (
                    existsPresent(TITLE) ||
                    existsPresent(USERNAME_INPUT) ||
                    existsPresent(PASSWORD_INPUT) ||
                    pageHasLoginText()
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

    fun isGone(): Boolean =
        loginFormIsGone()

    fun loginAsTestUser(): AuthModal {
        return login(
            username = TEST_LOGIN,
            password = TEST_PASSWORD
        )
    }

    fun openHelp(): HelpModal {
        waitUntilVisible()

        val helpLink = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToLoginFrameIfNeeded()

            driver.findElements(HELP_LINK)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        try {
            helpLink.click()
        } catch (e: Exception) {
            Utils.jsClick(driver, helpLink)
        }

        val help = HelpModal(driver)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            help.isVisible()
        }

        return help
    }

    fun login(username: String, password: String): AuthModal {
        waitUntilVisible()

        val loginInput = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToLoginFrameIfNeeded()

            driver.findElements(USERNAME_INPUT)
                .firstOrNull { element -> element.isDisplayed }
        }!!

        val passwordInput = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToLoginFrameIfNeeded()

            driver.findElements(PASSWORD_INPUT)
                .firstOrNull { element -> element.isDisplayed }
        }!!

        loginInput.clear()
        loginInput.sendKeys(username)

        passwordInput.clear()
        passwordInput.sendKeys(password)

        val submitButton = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToLoginFrameIfNeeded()

            val loadingIsGone = driver.findElements(LOGIN_LOADING)
                .none { element ->
                    try {
                        element.isDisplayed
                    } catch (e: Exception) {
                        false
                    }
                }

            val button = driver.findElements(LOGIN_BUTTON)
                .firstOrNull { element ->
                    val className = element.getAttribute("class") ?: ""

                    element.isDisplayed &&
                            element.isEnabled &&
                            !className.contains("is-disabled")
                }

            if (loadingIsGone) button else null
        }!!

        submitButton.click()

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            loginFormIsGone()
        }

        driver.switchTo().defaultContent()

        return this
    }

    private fun loginFormIsGone(): Boolean {
        driver.switchTo().defaultContent()

        if (loginFormVisibleHere()) {
            return false
        }

        val frames = driver.findElements(By.tagName("iframe"))

        for (frame in frames) {
            try {
                driver.switchTo().defaultContent()
                driver.switchTo().frame(frame)

                if (loginFormVisibleHere()) {
                    return false
                }
            } catch (e: Exception) {
                driver.switchTo().defaultContent()
            }
        }

        driver.switchTo().defaultContent()
        return true
    }

    private fun loginFormVisibleHere(): Boolean {
        return try {
            driver.findElements(USERNAME_INPUT).any { it.isDisplayed } ||
                    driver.findElements(PASSWORD_INPUT).any { it.isDisplayed } ||
                    driver.findElements(LOGIN_BUTTON).any { it.isDisplayed }
        } catch (e: Exception) {
            false
        }
    }

    private fun pageHasLoginText(): Boolean =
        driver.pageSource.contains("Войти", ignoreCase = true) ||
                driver.pageSource.contains("учётную запись", ignoreCase = true) ||
                driver.pageSource.contains("Имя пользователя", ignoreCase = true) ||
                driver.pageSource.contains("Пароль", ignoreCase = true)

    private fun existsPresent(selector: By): Boolean {
        return try {
            driver.findElements(selector).isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    fun openRegistration(): RegistrationModal {
        waitUntilVisible()

        val registerLink = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToLoginFrameIfNeeded()

            driver.findElements(REGISTER_LINK)
                .firstOrNull { element ->
                    element.isDisplayed && element.isEnabled
                }
        }!!

        try {
            registerLink.click()
        } catch (e: Exception) {
            Utils.jsClick(driver, registerLink)
        }

        val registration = RegistrationModal(driver)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            registration.isVisible()
        }

        return registration
    }


}