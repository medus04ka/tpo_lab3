import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class InteractiveMapPage(driver: WebDriver) : Page(driver) {

    companion object {
        private val BODY = By.xpath("//body")

        private val ANNOUNCEMENT_TITLE = By.xpath(
            "//h3[contains(@class,'announcement__title') and contains(normalize-space(), 'Журнал обновлений')]"
        )

        private val ANNOUNCEMENT_CLOSE = By.xpath(
            "//*[contains(@class,'announcement__close')]"
        )

        private val LOCATION_TITLE = By.xpath(
            "//h1[contains(@class,'layer-control__title')]"
        )

        private val HOYOWIKI_LINK = By.xpath(
            "//a[contains(@href,'wiki.hoyolab.com')]"
        )

        private val ANNOUNCEMENT_BUTTON = By.xpath(
            "//*[contains(@class,'mhy-map__mobile-announcement')]"
        )

        private val SETTINGS_BUTTON = By.xpath(
            "//*[contains(@class,'mhy-map__setting')]"
        )

        private val MAP_MARKER = By.xpath(
            "//*[contains(@class,'leaflet-marker-icon') and contains(@class,'mhy-game-gis-marker')]"
        )

        private val ZOOM_SLIDER = By.xpath(
            "//*[contains(@class,'zoomslider') and contains(@class,'knob')]"
        )
    }

    fun open(): InteractiveMapPage {
        driver.get(Utils.BASE_URL)
        Utils.getElementBySelector(driver, BODY)

        val wait = WebDriverWait(driver, Duration.ofSeconds(90))
        wait.until {
            driver.findElements(ANNOUNCEMENT_TITLE).any { element -> element.isDisplayed } ||
                    driver.findElements(LOCATION_TITLE).any { element -> element.isDisplayed }
        }

        return this
    }

    fun closeAnnouncementIfVisible(): InteractiveMapPage {
        if (Utils.exists(driver, ANNOUNCEMENT_CLOSE, 5)) {
            Utils.click(driver, ANNOUNCEMENT_CLOSE)
        }
        return this
    }

    fun isMapVisible(): Boolean =
        Utils.exists(driver, LOCATION_TITLE, 60)

    fun locationTitle(): String =
        Utils.getElementBySelector(driver, LOCATION_TITLE).text

    fun openAnnouncements(): AnnouncementModal {
        Utils.click(driver, ANNOUNCEMENT_BUTTON)
        return AnnouncementModal(driver)
    }

    fun openHoyoWiki() {
        Utils.click(driver, HOYOWIKI_LINK)
    }

    fun openSettings(): SettingsModal {
        Utils.click(driver, SETTINGS_BUTTON)
        return SettingsModal(driver)
    }

    fun openFirstMarker(): ObjectCard {
        Utils.getElementBySelector(driver, MAP_MARKER)
        val marker = driver.findElements(MAP_MARKER).first { it.isDisplayed }

        Actions(driver)
            .moveToElement(marker)
            .click()
            .perform()

        return ObjectCard(driver)
    }

    fun moveZoomSlider() {
        val slider = Utils.getElementBySelector(driver, ZOOM_SLIDER)

        Actions(driver)
            .clickAndHold(slider)
            .moveByOffset(-40, 0)
            .release()
            .perform()
    }

    fun zoomSliderStyle(): String =
        Utils.getElementBySelector(driver, ZOOM_SLIDER).getAttribute("style") ?: ""

    fun isCategoryVisible(category: String): Boolean =
        Utils.exists(driver, By.xpath("//*[contains(normalize-space(), '$category')]"), 20)

    fun clickCategory(category: String) {
        Utils.click(driver, By.xpath("//*[contains(normalize-space(), '$category')]"))
    }

    fun changeLocation(location: String) {
        Utils.click(driver, LOCATION_TITLE)
        Utils.click(driver, By.xpath("//*[contains(normalize-space(), '$location')]"))
    }

    fun pageContainsText(text: String): Boolean =
        driver.pageSource.contains(text, ignoreCase = true)
}