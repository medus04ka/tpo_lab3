import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class AnnouncementModal(driver: WebDriver) : Page(driver) {

    companion object {
        private val TITLE = By.xpath(
            "//h3[contains(@class,'announcement__title') and contains(normalize-space(), 'Журнал обновлений')]"
        )

        private val CLOSE = By.xpath(
            "//*[contains(@class,'announcement__close')]"
        )
    }

    fun isVisible(): Boolean =
        Utils.exists(driver, TITLE, 20)

    fun close() {
        Utils.click(driver, CLOSE, 20)
    }
}