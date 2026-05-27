import hoyolab.Utils
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.FindBy
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

        private val ANNOUNCEMENT_BUTTON = By.xpath(
            "//*[contains(@class,'mhy-map__mobile-announcement')]"
        )

        private val SETTINGS_BUTTON = By.xpath(
            "//*[contains(@class,'mhy-map__setting')]"
        )

        private val ROOM_SWIPER_ITEMS = By.xpath(
            "//*[contains(@class,'map-swiper-item')]"
        )

        private val ROOM_3D_MARKER = By.xpath(
            "//*[contains(@class,'mhy-game-gis-icon') and contains(@class,'mhy-game-gis-icon__strategy')]"
        )

        private val CONFIRM_ROOM_TRANSITION_TEXT = By.xpath(
            "//*[contains(normalize-space(), 'Перейти к соответствующей карте')]"
        )

        private val CONFIRM_ROOM_TRANSITION_BUTTON = By.xpath(
            "//*[contains(@class,'confirmation__btn__confirm') and contains(normalize-space(), 'ОK')]"
        )

        private val STEREO_MAP = By.xpath(
            "//*[contains(@class,'mhy-game-gis--is-stereo')]"
        )

        private val BACK_MAP_BUTTON = By.xpath(
            "//*[contains(@class,'back-map') and .//*[contains(normalize-space(), 'Закрыть')]]"
        )

        private val MAP_SWIPER = By.xpath(
            "//*[contains(@class,'map-swiper-list')]"
        )

        private val FLOOR_2_BUTTON = By.xpath(
            "//*[contains(@class,'map-layer-list-item') and contains(normalize-space(), 'этаж 2')]"
        )

        private val FLOOR_1_BUTTON = By.xpath(
            "//*[contains(@class,'map-layer-list-item') and contains(normalize-space(), 'этаж 1')]"
        )

        private val DIORAMA_BUTTON = By.xpath(
            "//*[contains(@class,'map-layer-list-item') and contains(normalize-space(), 'Модель диорамы')]"
        )

        private val ACTIVE_FLOOR = By.xpath(
            "//*[contains(@class,'map-layer-list-item') and contains(@class,'map-layer-list-item--active')]"
        )

        private val ZOOM_IN_BUTTON = By.cssSelector(
            ".leaflet-control-zoomslider-horizon-in"
        )

        private val ZOOM_OUT_BUTTON = By.cssSelector(
            ".leaflet-control-zoomslider-horizon-out"
        )

        private val ZOOM_KNOB = By.cssSelector(
            ".leaflet-control-zoomslider-horizon-knob"
        )

        private val MAP_ROOT = By.cssSelector(
            ".mhy-game-gis"
        )

        private val RECEIVED_SWITCH = By.xpath(
            "//*[contains(normalize-space(), 'Получено')]" +
                    "/ancestor::*[contains(@class,'item') or contains(@class,'row') or self::div][1]" +
                    "//*[contains(@class,'gt-switch')]"
        )

        private val RECEIVED_SWITCH_ON = By.xpath(
            "//*[contains(normalize-space(), 'Получено')]" +
                    "/ancestor::*[contains(@class,'item') or contains(@class,'row') or self::div][1]" +
                    "//*[contains(@class,'gt-switch') and contains(@class,'gt-switch--on')]"
        )


        private val RECEIVED_OBJECT_MARKER = By.xpath(
            "//*[contains(@class,'mhy-game-gis-icon') and contains(@style,'opacity')]"
        )

        private val HOYOWIKI_LINK = By.xpath(
            "//a[contains(@href,'wiki.hoyolab.com')] | " +
                    "//*[contains(@class,'mhy-map__wiki-link')]"
        )

        @FindBy(xpath = "//*[contains(@class,'leaflet-marker-icon') and contains(@class,'mhy-game-gis-marker')]")
        private val map_marker: WebElement? = null

//        private val MAP_MARKER = By.xpath(
//            "//*[contains(@class,'leaflet-marker-icon') and contains(@class,'mhy-game-gis-marker')]"
//        )

        private val USER_BUTTON = By.xpath(
            "//*[contains(@class,'mhy-map__btn-user') " +
                    "or contains(@class,'mhy-user') " +
                    "or contains(@class,'avatar') " +
                    "or contains(@class,'avatar__img')]"
        )

        private val LOGGED_USER_AVATAR = By.xpath(
            "//*[contains(@class,'avatar') " +
                    "and contains(@class,'popover-container') " +
                    "and .//*[contains(@class,'avatar__img')]]"
        )

        private val USER_MENU = By.xpath(
            "//*[contains(@class,'menu') and " +
                    ".//*[contains(normalize-space(), 'Учётная запись')] and " +
                    ".//*[contains(normalize-space(), 'Сменить персонажа')] and " +
                    ".//*[contains(normalize-space(), 'Выйти')]]"
        )

        private val ACCOUNT_MENU_ITEM = By.xpath(
            "//a[contains(@class,'menu-item') and contains(normalize-space(), 'Учётная запись')]"
        )

        private val CHANGE_CHARACTER_MENU_ITEM = By.xpath(
            "//*[contains(@class,'menu-item') and contains(normalize-space(), 'Сменить персонажа')]"
        )

        private val LOGOUT_MENU_ITEM = By.xpath(
            "//*[contains(@class,'menu-item--logout') and contains(normalize-space(), 'Выйти')]"
        )

        private val CHARACTER_SELECTOR_TITLE = By.xpath(
            "//*[contains(@class,'game-role-selector-sea__header__text') " +
                    "and contains(normalize-space(), 'Выбор персонажа')]"
        )

        private val CHARACTER_SERVER_DROPDOWN = By.xpath(
            "//*[contains(@class,'dropdown-menu__input') and .//*[contains(normalize-space(), 'Europe Server')]]"
        )

        private val EUROPE_SERVER_ITEM = By.xpath(
            "//*[contains(@class,'dropdown-menu__list__item') and .//*[contains(normalize-space(), 'Europe Server')]]"
        )

        private val CHARACTER_CONFIRM_BUTTON = By.xpath(
            "//*[contains(@class,'game-role-selector-sea__button') and .//*[contains(normalize-space(), 'Подтвердить')]]"
        )

        private val CHARACTER_SERVER_TITLE = By.xpath(
            "//*[contains(@class,'game-role-selector-sea__content__title') " +
                    "and contains(normalize-space(), 'Сервер:')]"
        )

        private val HOLO_MAP_ENTRANCE = By.xpath(
            "//*[contains(@class,'holo-map-entrance') and .//*[normalize-space()='Обзор голографической карты']]"
        )

        private val HOLO_MAP_PAGE_TITLE = By.xpath(
            "//*[contains(@class,'page-name-') and normalize-space()='Обзор голографической карты']"
        )

        private val HOLO_MAP_BACK_BUTTON = By.xpath(
            "//*[contains(@class,'close-') and .//*[contains(@class,'text-') and normalize-space()='Назад']]"
        )

        private val HOLO_ANY_CARD_NAME = By.xpath(
            "//*[contains(@class,'map-cover-name__inner-')]"
        )

        private val HOLO_FLOOR_1 = By.xpath(
            "//*[contains(@class,'floor-') and normalize-space()='1 этаж']"
        )

        private val HOLO_FLOOR_2 = By.xpath(
            "//*[contains(@class,'floor-') and normalize-space()='2 этаж']"
        )
    }

    fun open(): InteractiveMapPage {
        driver.get(Utils.BASE_URL)
        Utils.getPresentElement(driver, BODY, 30)

        WebDriverWait(driver, Duration.ofSeconds(90)).until {
            Utils.visible(driver, ANNOUNCEMENT_TITLE) ||
                    Utils.visible(driver, LOCATION_TITLE)
        }

        return this
    }

    fun closeAnnouncementIfVisible(): InteractiveMapPage {
        if (Utils.exists(driver, ANNOUNCEMENT_CLOSE, 5)) {
            Utils.click(driver, ANNOUNCEMENT_CLOSE, 20)
        }
        return this
    }

    private fun holoCardByName(name: String): By {
        return By.xpath(
            "//*[contains(@class,'map-cover-') " +
                    "and .//*[contains(@class,'map-cover-name__inner-') " +
                    "and normalize-space()='$name']]"
        )
    }

    private fun activeHoloCardByName(name: String): By {
        return By.xpath(
            "//*[contains(@class,'map-swiper-item--') " +
                    "and contains(@class,'active-') " +
                    "and .//*[contains(@class,'map-cover-name__inner-') " +
                    "and normalize-space()='$name']]"
        )
    }

    private fun switchToHoloFrameIfNeeded(vararg locators: By) {
        driver.switchTo().defaultContent()

        if (locators.any { locator -> driver.findElements(locator).isNotEmpty() }) {
            return
        }

        val frames = driver.findElements(By.tagName("iframe"))

        for (frame in frames) {
            driver.switchTo().defaultContent()
            driver.switchTo().frame(frame)

            if (locators.any { locator -> driver.findElements(locator).isNotEmpty() }) {
                return
            }
        }

        driver.switchTo().defaultContent()
    }

    private fun resetScrollToTop() {
        (driver as JavascriptExecutor).executeScript(
            """
        window.scrollTo(0, 0);
        document.documentElement.scrollTop = 0;
        document.body.scrollTop = 0;
        document.querySelectorAll('*').forEach(function(el) {
            if (el.scrollTop) el.scrollTop = 0;
        });
        """.trimIndent()
        )
    }

    fun openHoloMapOverview(): InteractiveMapPage {
        switchToHoloFrameIfNeeded(HOLO_MAP_ENTRANCE)

        val entrance = Utils.getElementBySelector(driver, HOLO_MAP_ENTRANCE, 30)

        Utils.scrollIntoView(driver, entrance)
        Utils.jsClick(driver, entrance)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            driver.currentUrl.contains("origin_map_id") ||
                    run {
                        switchToHoloFrameIfNeeded(HOLO_MAP_PAGE_TITLE, HOLO_ANY_CARD_NAME)
                        driver.findElements(HOLO_MAP_PAGE_TITLE).isNotEmpty() ||
                                driver.findElements(HOLO_ANY_CARD_NAME).isNotEmpty()
                    }
        }

        return this
    }

    fun isHoloMapOverviewOpened(): Boolean {
        switchToHoloFrameIfNeeded(HOLO_MAP_PAGE_TITLE, HOLO_ANY_CARD_NAME)

        return driver.currentUrl.contains("origin_map_id") ||
                driver.findElements(HOLO_MAP_PAGE_TITLE).isNotEmpty() ||
                driver.findElements(HOLO_ANY_CARD_NAME).isNotEmpty()
    }

    fun waitHoloCardsLoaded(): InteractiveMapPage {
        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToHoloFrameIfNeeded(HOLO_ANY_CARD_NAME)
            driver.findElements(HOLO_ANY_CARD_NAME).size >= 5
        }

        return this
    }

    fun clickHoloCardAndWaitActive(name: String): InteractiveMapPage {
        switchToHoloFrameIfNeeded(HOLO_ANY_CARD_NAME)

        val card = Utils.getElementBySelector(driver, holoCardByName(name), 30)

        Utils.jsClick(driver, card)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToHoloFrameIfNeeded(HOLO_ANY_CARD_NAME)
            driver.findElements(activeHoloCardByName(name)).isNotEmpty()
        }

        return this
    }

    fun isHoloCardActive(name: String): Boolean {
        switchToHoloFrameIfNeeded(HOLO_ANY_CARD_NAME)

        return driver.findElements(activeHoloCardByName(name)).isNotEmpty()
    }

    fun hasHoloFloors(): Boolean {
        switchToHoloFrameIfNeeded(HOLO_FLOOR_1, HOLO_FLOOR_2)

        return driver.findElements(HOLO_FLOOR_1).isNotEmpty() &&
                driver.findElements(HOLO_FLOOR_2).isNotEmpty()
    }

    fun clickHoloFloor1(): InteractiveMapPage {
        switchToHoloFrameIfNeeded(HOLO_FLOOR_1)

        val floor = Utils.getElementBySelector(driver, HOLO_FLOOR_1, 20)

        Utils.jsClick(driver, floor)

        Thread.sleep(500)

        return this
    }

    fun clickHoloFloor2(): InteractiveMapPage {
        switchToHoloFrameIfNeeded(HOLO_FLOOR_2)

        val floor = Utils.getElementBySelector(driver, HOLO_FLOOR_2, 20)

        Utils.jsClick(driver, floor)

        Thread.sleep(500)

        return this
    }

    fun closeHoloMapOverview(): InteractiveMapPage {
        switchToHoloFrameIfNeeded(HOLO_MAP_BACK_BUTTON)

        resetScrollToTop()

        val backButton = Utils.getElementBySelector(driver, HOLO_MAP_BACK_BUTTON, 20)

        Utils.jsClick(driver, backButton)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            switchToHoloFrameIfNeeded(HOLO_MAP_ENTRANCE)
            driver.findElements(HOLO_MAP_ENTRANCE).isNotEmpty()
        }

        return this
    }

    fun isHoloMapOverviewClosed(): Boolean {
        switchToHoloFrameIfNeeded(HOLO_MAP_ENTRANCE)

        return driver.findElements(HOLO_MAP_ENTRANCE).isNotEmpty()
    }

    fun isMapVisible(): Boolean =
        Utils.exists(driver, LOCATION_TITLE, 60)

    fun locationTitle(): String =
        Utils.getElementBySelector(driver, LOCATION_TITLE, 60).text

    fun pageContainsText(text: String): Boolean =
        driver.pageSource.contains(text)

    fun openAnnouncements(): AnnouncementModal {
        Utils.click(driver, ANNOUNCEMENT_BUTTON, 20)
        return AnnouncementModal(driver)
    }

    fun openHoyoWiki() {
        Utils.click(driver, HOYOWIKI_LINK, 20)
    }

    fun openSettings(): SettingModal {
        Utils.click(driver, SETTINGS_BUTTON, 20)
        return SettingModal(driver)
    }

    fun openFirstMarker(): ObjectCard {
        Utils.getElementBySelector(driver, map_marker as By, 60)

        val marker = driver.findElements(map_marker as By)
            .first { safeDisplayed(it) }

        Actions(driver)
            .moveToElement(marker)
            .click()
            .perform()

        return ObjectCard(driver)
    }

    fun lofyAdmZone(): InteractiveMapPage {
        driver.get(
            "https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=ru-ru&plat_type=pc#/map/49"
        )

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            Utils.exists(driver, map_marker as By, 5) ||
                    pageContainsText("Административный район1") ||
                    isMapVisible()
        }

        closeAnnouncementIfVisible()

        return this
    }

    fun penaconyHotel(): InteractiveMapPage {
        driver.get(
            "https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=ru-ru&plat_type=pc#/map/149"
        )

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            Utils.exists(driver, map_marker as By, 5) ||
                    pageContainsText("Золотой Миг") ||
                    isMapVisible()
        }

        closeAnnouncementIfVisible()

        return this
    }

    fun openIsolatedZone(): InteractiveMapPage {
        driver.get(
            "https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=ru-ru&plat_type=pc#/map/135"
        )

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            Utils.exists(driver, map_marker as By, 5) ||
                    pageContainsText("Изолированная зона") ||
                    isMapVisible()
        }

        closeAnnouncementIfVisible()

        return this
    }

    fun openReceivedObjectMarker(): ObjectCard {
        val receivedObjectMarker = By.xpath(
            "//*[contains(@class,'leaflet-marker-icon')]" +
                    "[.//*[contains(@style,'2464c384cb4ae8d5e36585e9065c0cee')]]"
        )

        Utils.click(driver, receivedObjectMarker, 30)

        val card = ObjectCard(driver)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            card.isVisible()
        }

        return card
    }

    fun isCategoryVisible(category: String): Boolean =
        Utils.exists(driver, categorySelector(category), 20)

    fun clickCategory(category: String) {
        Utils.click(driver, categorySelector(category), 20)
    }

    private fun categorySelector(category: String): By {
        return when (category) {
            "Видимые сундуки" -> By.xpath(
                "//*[contains(normalize-space(), 'Видимые сундуки') " +
                        "or contains(normalize-space(), 'Видимый сундук')]"
            )

            else -> By.xpath(
                "//*[contains(normalize-space(), '$category')]"
            )
        }
    }

    fun openBaseZoneDirectly(): InteractiveMapPage {
        driver.get(
            "https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=ru-ru#/map/39"
        )

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            pageContainsText("Базовая зона") ||
                    pageContainsText("Фрагментум") ||
                    pageContainsText("Сундук-головоломка") ||
                    isMapVisible()
        }

        closeAnnouncementIfVisible()

        return this
    }

    fun changeLocation(location: String) {
        Utils.click(driver, LOCATION_TITLE, 20)

        Utils.click(
            driver,
            By.xpath("//*[contains(normalize-space(), '$location')]"),
            20
        )
    }

    fun changeZone(zone: String): InteractiveMapPage {
        val zoneSelector = By.xpath(
            "//*[contains(@class,'map-switch__item-name') and contains(normalize-space(), '$zone')] " +
                    "| //*[contains(@class,'map-switch__wrapper') and contains(normalize-space(), '$zone')] " +
                    "| //*[contains(normalize-space(), '$zone')]"
        )

        Utils.click(driver, zoneSelector, 30)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            pageContainsText(zone) || isMapVisible()
        }

        return this
    }

    fun openAuthWindow(): AuthModal {
        val auth = AuthModal(driver)

        if (auth.isVisible()) {
            return auth
        }

        Utils.click(driver, USER_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            auth.isVisible()
        }

        return auth
    }

    fun waitUntilLoggedIn(): InteractiveMapPage {
        driver.switchTo().defaultContent()

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            AuthModal(driver).isGone() &&
                    Utils.visible(driver, LOGGED_USER_AVATAR)
        }

        return this
    }

    fun isLoggedIn(): Boolean {
        driver.switchTo().defaultContent()
        return Utils.visible(driver, LOGGED_USER_AVATAR)
    }

    fun openCharacterServerDropdown() {
        Utils.click(driver, CHARACTER_SERVER_DROPDOWN, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            Utils.visible(driver, EUROPE_SERVER_ITEM) ||
                    pageContainsText("America Server") ||
                    pageContainsText("Asia Server")
        }
    }

    fun selectEuropeServer() {
        Utils.click(driver, EUROPE_SERVER_ITEM, 6)

        WebDriverWait(driver, Duration.ofSeconds(7)).until {
            pageContainsText("Europe Server")
        }
    }

    fun confirmCharacterSelection() {
        Utils.click(driver, CHARACTER_CONFIRM_BUTTON, 6)

        WebDriverWait(driver, Duration.ofSeconds(7)).until {
            !pageContainsText("Выбор персонажа") || isMapVisible()
        }
    }

    fun openLoggedInUserMenu(): InteractiveMapPage {
        waitUntilLoggedIn()

        driver.switchTo().defaultContent()

        if (Utils.visible(driver, USER_MENU)) {
            return this
        }

        val avatar = Utils.getElementBySelector(driver, LOGGED_USER_AVATAR, 40)

        Actions(driver)
            .moveToElement(avatar)
            .click()
            .perform()

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            Utils.visible(driver, USER_MENU) ||
                    pageContainsText("Учётная запись") &&
                    pageContainsText("Сменить персонажа") &&
                    pageContainsText("Выйти")
        }

        return this
    }

    fun clickLogoutItem() {
        driver.switchTo().defaultContent()

        Utils.click(driver, LOGOUT_MENU_ITEM, 6)

        WebDriverWait(driver, Duration.ofSeconds(7)).until {
            !Utils.visible(driver, USER_MENU)
        }
    }

    fun hasAccountItem(): Boolean =
        Utils.exists(driver, ACCOUNT_MENU_ITEM, 10)

    fun hasChangeCharacterItem(): Boolean =
        Utils.exists(driver, CHANGE_CHARACTER_MENU_ITEM, 10)

    fun hasLogoutItem(): Boolean =
        Utils.exists(driver, LOGOUT_MENU_ITEM, 10)

    fun clickAccountItem() {
        val oldTabs = driver.windowHandles.toSet()

        Utils.click(driver, ACCOUNT_MENU_ITEM, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            driver.windowHandles.size > oldTabs.size
        }

        val newTab = driver.windowHandles.first { it !in oldTabs }
        driver.switchTo().window(newTab)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            driver.currentUrl.contains("hoyolab.com") ||
                    driver.currentUrl.contains("accountCenter")
        }
    }

    fun closeCurrentTabAndReturnToMap(): InteractiveMapPage {
        driver.close()

        val mainTab = driver.windowHandles.first()
        driver.switchTo().window(mainTab)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            isMapVisible()
        }

        return this
    }

    fun clickChangeCharacterItem() {
        Utils.click(driver, CHANGE_CHARACTER_MENU_ITEM, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            Utils.visible(driver, CHARACTER_SELECTOR_TITLE) ||
                    pageContainsText("Выбор персонажа")
        }
    }

    fun isCharacterSelectorVisible(): Boolean =
        Utils.exists(driver, CHARACTER_SELECTOR_TITLE, 10) ||
                pageContainsText("Выбор персонажа")

    fun hasCharacterServerSelector(): Boolean =
        Utils.exists(driver, CHARACTER_SERVER_TITLE, 10) ||
                pageContainsText("Europe Server")

    fun openReceivedObjectMarker2(): InteractiveMapPage {
        Utils.click(driver, RECEIVED_OBJECT_MARKER, 30)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            pageContainsText("Получено")
        }

        return this
    }

    fun roomSwiperItemsCount(): Int {
        return driver.findElements(ROOM_SWIPER_ITEMS).size
    }

    fun activeRoomIndex(): Int {
        val items = driver.findElements(ROOM_SWIPER_ITEMS)

        return items.indexOfFirst {
            val className = it.getAttribute("class") ?: ""
            className.contains("active")
        }
    }

    fun clickRoomPreviewByIndex(index: Int): InteractiveMapPage {
        val beforeActiveIndex = activeRoomIndex()

        val items = driver.findElements(ROOM_SWIPER_ITEMS)

        Utils.scrollIntoView(driver, items[index])
        items[index].click()

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            activeRoomIndex() == index && activeRoomIndex() != beforeActiveIndex
        }

        return this
    }

    fun markCurrentObjectAsReceived(): InteractiveMapPage {
        if (Utils.visible(driver, RECEIVED_SWITCH_ON)) {
            return this
        }

        Utils.click(driver, RECEIVED_SWITCH, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            Utils.visible(driver, RECEIVED_SWITCH_ON)
        }

        return this
    }

    fun open3DRoomMap(): InteractiveMapPage {
        driver.get(
            "https://act.hoyolab.com/sr/app/interactive-map/index.html?hyl_presentation_style=fullscreen&lang=ru-ru&plat_type=pc#/map/214?center=128.00,-427.00&zoom=-1.00&shown_types=24,446,447,512,513,514,515,516,521,522,459,460,499,502,659,660,661,662,663,666,667,668"
        )

        WebDriverWait(driver, Duration.ofSeconds(60)).until {
            isMapVisible() || Utils.visible(driver, ROOM_3D_MARKER)
        }

        closeAnnouncementIfVisible()

        return this
    }

    fun click3DRoomMarker(): InteractiveMapPage {
        Utils.click(driver, ROOM_3D_MARKER, 30)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            Utils.visible(driver, CONFIRM_ROOM_TRANSITION_TEXT)
        }

        return this
    }

    fun confirm3DRoomTransition(): InteractiveMapPage {
        Utils.click(driver, CONFIRM_ROOM_TRANSITION_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(40)).until {
            Utils.visible(driver, STEREO_MAP) ||
                    Utils.visible(driver, BACK_MAP_BUTTON) ||
                    Utils.visible(driver, MAP_SWIPER)
        }

        return this
    }

    fun is3DRoomOpened(): Boolean {
        return Utils.visible(driver, STEREO_MAP) ||
                Utils.visible(driver, BACK_MAP_BUTTON) ||
                Utils.visible(driver, MAP_SWIPER)
    }

    fun currentActiveFloor(): String {
        return Utils.getElementBySelector(driver, ACTIVE_FLOOR, 20).text.trim()
    }

    fun openFloor1(): InteractiveMapPage {
        Utils.click(driver, FLOOR_1_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            currentActiveFloor().contains("этаж 1")
        }

        return this
    }

    fun openFloor2(): InteractiveMapPage {
        Utils.click(driver, FLOOR_2_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            currentActiveFloor().contains("этаж 2")
        }

        return this
    }

    fun openDioramaModel(): InteractiveMapPage {
        Utils.click(driver, DIORAMA_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            currentActiveFloor().contains("Модель диорамы")
        }

        return this
    }

    fun close3DRoom(): InteractiveMapPage {
        Utils.click(driver, BACK_MAP_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            !Utils.visible(driver, STEREO_MAP) &&
                    Utils.visible(driver, FLOOR_1_BUTTON) &&
                    Utils.visible(driver, FLOOR_2_BUTTON) &&
                    Utils.visible(driver, DIORAMA_BUTTON)
        }

        return this
    }

    fun currentZoomState(): String {
        val rootStyle = Utils.getElementBySelector(driver, MAP_ROOT, 20)
            .getAttribute("style") ?: ""

        val knobStyle = Utils.getElementBySelector(driver, ZOOM_KNOB, 20)
            .getAttribute("style") ?: ""

        return "$rootStyle | $knobStyle"
    }

    fun zoomIn(): InteractiveMapPage {
        val before = currentZoomState()

        Utils.click(driver, ZOOM_IN_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(10)).until {
            currentZoomState() != before
        }

        return this
    }

    fun zoomOut(): InteractiveMapPage {
        val before = currentZoomState()

        Utils.click(driver, ZOOM_OUT_BUTTON, 20)

        WebDriverWait(driver, Duration.ofSeconds(10)).until {
            currentZoomState() != before
        }

        return this
    }

    private fun safeDisplayed(element: WebElement): Boolean {
        return try {
            element.isDisplayed
        } catch (e: Exception) {
            false
        }
    }
}