import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait

class LoginModal(
    driver: WebDriver,
    wait: WebDriverWait,
    actions: Actions
) : BasePage(driver, wait, actions) {

    companion object {
        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Войти в учётную запись')]"
        )

        private val USERNAME_INPUT = By.xpath(
            "//input[contains(@placeholder, 'Имя пользователя') or contains(@placeholder, 'эл. почта')]"
        )

        private val PASSWORD_INPUT = By.xpath(
            "//input[contains(@placeholder, 'Пароль') or @type='password']"
        )

        private val LOGIN_BUTTON = By.xpath(
            "//*[contains(normalize-space(), 'Войти') and (self::button or self::div)]"
        )

        private val CLOSE_BUTTON = By.xpath(
            "//*[contains(@class,'close') or contains(@class,'Close')]"
        )
    }

    fun isVisible(): Boolean =
        waitVisibleLong(TITLE).isDisplayed

    fun isUsernameInputVisible(): Boolean =
        waitVisibleLong(USERNAME_INPUT).isDisplayed

    fun isPasswordInputVisible(): Boolean =
        waitVisibleLong(PASSWORD_INPUT).isDisplayed

    fun isLoginButtonVisible(): Boolean =
        waitVisibleLong(LOGIN_BUTTON).isDisplayed

    fun close(): LoginModal {
        clickSafely(CLOSE_BUTTON)
        return this
    }
}