import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class SettingsModal(driver: WebDriver) : Page(driver) {
    //TODO: дописать вход в аккаунт

    companion object {
        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Настройки')]"
        )

        private val CHANGE_LANGUAGE = By.xpath(
            "//*[contains(normalize-space(), 'Сменить язык') or contains(normalize-space(), 'Language')]"
        )

        private val HIDE_COLLECTED = By.xpath(
            "//*[contains(normalize-space(), 'Скрыть полученные метки')]"
        )

        private val CHEST_PROGRESS = By.xpath(
            "//*[contains(normalize-space(), 'Прогресс сбора видимых сундуков')]"
        )
    }

    fun isVisible(): Boolean =
        Utils.exists(driver, TITLE, 20)

    fun hasHideCollectedOption(): Boolean =
        Utils.exists(driver, HIDE_COLLECTED, 20)

    fun hasChestProgressOption(): Boolean =
        Utils.exists(driver, CHEST_PROGRESS, 20)

    fun hasLanguageOption(): Boolean =
        Utils.exists(driver, CHANGE_LANGUAGE, 20)

    fun openLanguageList() {
        Utils.click(driver, CHANGE_LANGUAGE)
    }

    fun selectLanguage(language: String) {
        Utils.click(driver, By.xpath("//*[contains(normalize-space(), '$language')]"))
    }
}