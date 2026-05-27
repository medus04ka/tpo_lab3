import hoyolab.SharedDriver
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver

abstract class BaseTest {

    protected lateinit var driver: WebDriver
    protected lateinit var page: InteractiveMapPage

    @BeforeEach
    fun openMapBeforeEachTest() {
        driver = SharedDriver.getDriver()
        page = InteractiveMapPage(driver)

        page.open()
        page.closeAnnouncementIfVisible()
    }
}