import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class ObjectCard(driver: WebDriver) : Page(driver) {

    companion object {
        private val CARD = By.xpath(
            "//*[contains(normalize-space(), 'Название:') " +
                    "or contains(normalize-space(), 'Категория:') " +
                    "or contains(normalize-space(), 'Получено')]"
        )

        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Название:')]"
        )

        private val CATEGORY = By.xpath(
            "//*[contains(normalize-space(), 'Категория:')]"
        )

        private val IMAGE = By.xpath(
            "//*[contains(normalize-space(), 'Название:')]" +
                    "/ancestor::*[contains(@class,'popup') or contains(@class,'card') or contains(@class,'leaflet') or self::div][1]//img"
        )

        private val RECEIVED_TEXT = By.xpath(
            "//*[contains(normalize-space(), 'Получено')]"
        )

        private val RECEIVED_SWITCH = By.xpath(
            "//*[contains(normalize-space(), 'Получено')]" +
                    "/following::*[contains(@class,'switch') or contains(@class,'el-switch')][1] " +
                    "| //*[contains(normalize-space(), 'Получено')]" +
                    "/following::*[contains(@class,'el-switch__core')][1]"
        )

        private val GO_TO_FLOOR = By.xpath(
            "//*[contains(normalize-space(), 'Перейти на соответствующий этаж')]"
        )
    }

    fun isVisible(): Boolean =
        Utils.exists(driver, CARD, 20) ||
                Utils.exists(driver, TITLE, 20) ||
                Utils.exists(driver, CATEGORY, 20)

    fun hasDescription(): Boolean =
        Utils.exists(driver, TITLE, 10) &&
                Utils.exists(driver, CATEGORY, 10)

    fun hasImage(): Boolean =
        Utils.exists(driver, IMAGE, 10)


    fun clickReceived() {
        if (Utils.exists(driver, RECEIVED_SWITCH, 5)) {
            Utils.click(driver, RECEIVED_SWITCH, 20)
        } else {
            Utils.click(driver, RECEIVED_TEXT, 20)
        }
    }

    fun hasGoToFloorButton(): Boolean =
        Utils.exists(driver, GO_TO_FLOOR, 10)

    fun goToFloor() {
        Utils.click(driver, GO_TO_FLOOR, 20)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            driver.pageSource.contains("Название:", ignoreCase = true) ||
                    driver.pageSource.contains("Категория:", ignoreCase = true) ||
                    driver.findElements(By.xpath("//*[contains(@class,'leaflet-marker-icon')]")).isNotEmpty()
        }
    }
}