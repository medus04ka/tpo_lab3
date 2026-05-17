import hoyolab.Utils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.WebDriver

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTest {

    protected lateinit var driver: WebDriver
    protected lateinit var page: InteractiveMapPage

    @BeforeAll
    fun setUpDriver() {
        driver = Utils.getDrivers().first()
        page = InteractiveMapPage(driver)
    }

    @BeforeEach
    fun openMapBeforeEachTest() {
        page.open()
        page.closeAnnouncementIfVisible()
    }

    @AfterAll
    fun tearDownDriver() {
        driver.quit()
    }
}