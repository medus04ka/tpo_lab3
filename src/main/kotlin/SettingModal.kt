import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SettingModal(driver: WebDriver) : Page(driver) {

    companion object {
        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Настройки') or contains(normalize-space(), 'Settings')]"
        )

        private val CHANGE_LANGUAGE = By.xpath(
            "//*[contains(normalize-space(), 'Сменить язык') or contains(normalize-space(), 'Language')]"
        )

        private val LANGUAGE_DROPDOWN_BUTTON = By.xpath(
            "//*[contains(@class,'lang-text')]/ancestor::*[contains(@class,'right')][1]"
        )

        private val LANGUAGE_ITEM_ENGLISH = By.xpath(
            "//*[contains(@class,'lang-item') and normalize-space()='English']"
        )

        private val HIDE_COLLECTED_SWITCH_ON = By.xpath(
            "//*[contains(@class,'item') and .//*[contains(normalize-space(), 'Скрыть полученные метки')]]" +
                    "//*[contains(@class,'gt-switch') and contains(@class,'gt-switch--on')]"
        )

        private val CHEST_PROGRESS_SWITCH_ON = By.xpath(
            "//*[contains(@class,'item') and .//*[contains(normalize-space(), 'Прогресс сбора видимых сундуков')]]" +
                    "//*[contains(@class,'gt-switch') and contains(@class,'gt-switch--on')]"
        )

        private val HIDE_COLLECTED = By.xpath(
            "//*[contains(normalize-space(), 'Скрыть полученные метки')]"
        )

        private val HIDE_COLLECTED_SWITCH = By.xpath(
            "//*[contains(@class,'item') and .//*[contains(normalize-space(), 'Скрыть полученные метки')]]" +
                    "//*[contains(@class,'gt-switch') or contains(@class,'el-switch')]"
        )

        private val CHEST_PROGRESS = By.xpath(
            "//*[contains(normalize-space(), 'Прогресс сбора видимых сундуков')]"
        )

        private val CHEST_PROGRESS_SWITCH = By.xpath(
            "//*[contains(@class,'item') and .//*[contains(normalize-space(), 'Прогресс сбора видимых сундуков')]]" +
                    "//*[contains(@class,'gt-switch') or contains(@class,'el-switch')]"
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
        Utils.click(driver, LANGUAGE_DROPDOWN_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            Utils.visible(driver, LANGUAGE_ITEM_ENGLISH)
        }
    }

    fun selectLanguage(language: String) {
        val languageItem = By.xpath(
            "//*[contains(@class,'lang-item') and normalize-space()='$language']"
        )

        Utils.click(driver, languageItem, 20)

        WebDriverWait(driver, Duration.ofSeconds(40)).until {
            driver.pageSource.contains("Settings") ||
                    driver.pageSource.contains("Books") ||
                    driver.pageSource.contains("Points of Interest") ||
                    driver.pageSource.contains("Language")
        }
    }

    fun enableHideCollected() {
        if (Utils.visible(driver, HIDE_COLLECTED_SWITCH_ON)) {
            return
        }

        Utils.click(driver, HIDE_COLLECTED_SWITCH, 20)

        WebDriverWait(driver, Duration.ofSeconds(10)).until {
            Utils.visible(driver, HIDE_COLLECTED_SWITCH_ON)
        }
    }

    fun enableChestProgressHiding() {
        if (Utils.visible(driver, CHEST_PROGRESS_SWITCH_ON)) {
            return
        }

        Utils.click(driver, CHEST_PROGRESS_SWITCH, 20)

        WebDriverWait(driver, Duration.ofSeconds(10)).until {
            Utils.visible(driver, CHEST_PROGRESS_SWITCH_ON)
        }
    }
}