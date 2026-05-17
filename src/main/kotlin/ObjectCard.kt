import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ObjectCard(driver: WebDriver) : Page(driver) {

    companion object {
        private val TITLE = By.xpath(
            "//*[contains(normalize-space(), 'Название:') or contains(@class,'title')]"
        )

        private val CATEGORY = By.xpath(
            "//*[contains(normalize-space(), 'Категория')]"
        )

        private val IMAGE = By.xpath(
            "//*[contains(@class,'popup') or contains(@class,'card') or contains(@class,'detail')]//img"
        )

        private val RECEIVED = By.xpath(
            "//*[contains(normalize-space(), 'Получено')]"
        )

        private val GO_TO_FLOOR = By.xpath(
            "//*[contains(normalize-space(), 'Перейти на соответствующий этаж')]"
        )
    }

    fun isVisible(): Boolean =
        Utils.exists(driver, TITLE, 20) ||
                Utils.exists(driver, CATEGORY, 20)

    fun hasDescription(): Boolean =
        Utils.exists(driver, TITLE, 10) ||
                Utils.exists(driver, CATEGORY, 10) ||
                Utils.exists(driver, IMAGE, 10)

    fun hasReceived(): Boolean =
        Utils.exists(driver, RECEIVED, 5)

    fun clickReceived() {
        Utils.click(driver, RECEIVED)
    }

    fun hasGoToFloorButton(): Boolean =
        Utils.exists(driver, GO_TO_FLOOR, 5)

    fun goToFloor() {
        Utils.click(driver, GO_TO_FLOOR)
    }
}