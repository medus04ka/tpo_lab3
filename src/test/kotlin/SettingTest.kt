import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SettingTest : BaseTest() {

    @Test
    fun settingOpen() {
        val settings = page.openSettings()

        assertTrue(
            settings.isVisible(),
            "После нажатия кнопки настроек должно открыться окно Настройки"
        )
    }

    @Test
    fun hideMark() {
        val settings = page.openSettings()
        assertTrue(
            settings.hasHideCollectedOption(),
            "В настройках должен отображаться пункт Скрыть полученные метки"
        )
    }

    @Test
    fun baggageMars() {
        val settings = page.openSettings()

        assertTrue(
            settings.hasChestProgressOption(),
            "В настройках должен отображаться пункт прогресса сбора видимых сундуков"
        )
    }

    @Test
    fun language() {
        val settings = page.openSettings()

        assertTrue(
            settings.hasLanguageOption(),
            "В настройках должен отображаться пункт смены языка"
        )
    }

    @Test
    fun clickCollected() {
        val settings = page.openSettings()
        assertTrue(
            settings.hasHideCollectedOption(),
            "Настройка скрытия полученных меток должна быть доступна"
        )
        settings.enableHideCollected()

    }

    @Test
    fun clickChest() {
        val settings = page.openSettings()
        assertTrue(
            settings.hasChestProgressOption(),
            "Настройка прогресса сундуков должна быть доступна"
        )
        settings.enableChestProgressHiding()
    }

    @Test
    fun languageEnglish() {
        val settings = page.openSettings()
        assertTrue(
            settings.hasLanguageOption(),
            "В настройках должен быть пункт смены языка"
        )
        settings.openLanguageList()
        settings.selectLanguage("English")
        assertTrue(
            page.pageContainsText("Settings") ||
                    page.pageContainsText("Books") ||
                    page.pageContainsText("Points of Interest") ||
                    page.pageContainsText("Language"),
            "После смены языка интерфейс должен отображаться на английском"
        )
        WebDriverWait(driver, Duration.ofSeconds(10))
    }
}