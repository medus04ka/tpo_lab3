import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class HoyoWikiTest : BaseTest() {

    @Test
    fun hoyoWiki() {
        val oldWindows = driver.windowHandles

        page.openHoyoWiki()

        WebDriverWait(driver, Duration.ofSeconds(25))
            .until(ExpectedConditions.numberOfWindowsToBe(oldWindows.size + 1))

        val newWindow = driver.windowHandles.first { it !in oldWindows }
        driver.switchTo().window(newWindow)

        assertTrue(
            driver.currentUrl.contains("wiki.hoyolab.com"),
            "После нажатия должна открыться страница HoYoWiki"
        )
    }
}