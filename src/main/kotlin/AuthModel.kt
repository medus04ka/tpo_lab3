import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class AuthModal(driver: WebDriver) : Page(driver) {
    //TODO: дописать вход в аккаунт)()((()()())()(((

    companion object {
        private val LOGIN_TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Войти') or contains(normalize-space(), 'учётную запись')]"
        )

        private val LOGIN_INPUT = By.xpath(
            "//input[@type='text' or @type='email' or contains(@placeholder, 'почта') or contains(@placeholder, 'Имя пользователя')]"
        )

        private val PASSWORD_INPUT = By.xpath(
            "//input[@type='password' or contains(@placeholder, 'Пароль')]"
        )

        private val REGISTER_LINK = By.xpath(
            "//*[contains(normalize-space(), 'Регистрация') or contains(normalize-space(), 'Зарегистрироваться')]"
        )

        private val FORGOT_PASSWORD_LINK = By.xpath(
            "//*[contains(normalize-space(), 'Забыли пароль') or contains(normalize-space(), 'Восстановить пароль')]"
        )
    }

    fun isVisible(): Boolean =
        Utils.exists(driver, LOGIN_TITLE, 20)

    fun hasLoginInput(): Boolean =
        Utils.exists(driver, LOGIN_INPUT, 20)

    fun hasPasswordInput(): Boolean =
        Utils.exists(driver, PASSWORD_INPUT, 20)

    fun openRegistration() {
        Utils.click(driver, REGISTER_LINK)
    }

    fun openPasswordRecovery() {
        Utils.click(driver, FORGOT_PASSWORD_LINK)
    }
}