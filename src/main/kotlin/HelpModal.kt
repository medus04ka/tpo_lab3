import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class HelpModal(driver: WebDriver) : Page(driver) {

    companion object {
        private val TITLE = By.xpath(
            "//*[contains(@class,'el-dialog__title') and normalize-space()='Столкнулись с проблемами?']"
        )

        private val HELP_DIALOG = By.xpath(
            "//*[@role='dialog' and .//*[normalize-space()='Столкнулись с проблемами?']]"
        )

        private val FORGOT_PASSWORD_BUTTON = By.xpath(
            "//*[@role='dialog' and .//*[normalize-space()='Столкнулись с проблемами?']]" +
                    "//*[normalize-space()='Забыли пароль?']"
        )

        private val QUESTIONS_BUTTON = By.xpath(
            "//*[@role='dialog' and .//*[normalize-space()='Столкнулись с проблемами?']]" +
                    "//*[normalize-space()='Вопросы и ответы']"
        )

        private val FORGOT_PASSWORD_LINK = By.xpath(
            "//*[@role='dialog' and .//*[normalize-space()='Столкнулись с проблемами?']]" +
                    "//a[.//*[normalize-space()='Забыли пароль?'] or contains(normalize-space(), 'Забыли пароль?')]"
        )

        private val QUESTIONS_LINK = By.xpath(
            "//*[@role='dialog' and .//*[normalize-space()='Столкнулись с проблемами?']]" +
                    "//a[.//*[normalize-space()='Вопросы и ответы'] or contains(normalize-space(), 'Вопросы и ответы')]"
        )

        private val CLOSE_BUTTON = By.xpath(
            "//*[@role='dialog' and .//*[normalize-space()='Столкнулись с проблемами?']]" +
                    "//button[contains(@class,'el-dialog__headerbtn') or @aria-label='close']"
        )
    }

    fun waitUntilVisible(): HelpModal {
        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToHelpFrameIfNeeded()

            existsPresent(TITLE) ||
                    existsPresent(HELP_DIALOG) ||
                    pageHasHelpText()
        }

        return this
    }

    fun switchToHelpFrameIfNeeded(): HelpModal {
        driver.switchTo().defaultContent()

        if (
            existsPresent(TITLE) ||
            existsPresent(HELP_DIALOG) ||
            pageHasHelpText()
        ) {
            return this
        }

        val frames = driver.findElements(By.tagName("iframe"))

        for (frame in frames) {
            try {
                driver.switchTo().defaultContent()
                driver.switchTo().frame(frame)

                if (
                    existsPresent(TITLE) ||
                    existsPresent(HELP_DIALOG) ||
                    existsPresent(FORGOT_PASSWORD_BUTTON) ||
                    existsPresent(QUESTIONS_BUTTON) ||
                    pageHasHelpText()
                ) {
                    return this
                }
            } catch (e: Exception) {
                driver.switchTo().defaultContent()
            }
        }

        driver.switchTo().defaultContent()
        return this
    }

    fun isVisible(): Boolean {
        return try {
            waitUntilVisible()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun hasForgotPasswordButton(): Boolean {
        switchToHelpFrameIfNeeded()

        return existsPresent(FORGOT_PASSWORD_BUTTON)
    }

    fun hasQuestionsButton(): Boolean {
        switchToHelpFrameIfNeeded()

        return existsPresent(QUESTIONS_BUTTON)
    }

    fun openForgotPasswordAndReturn(): HelpModal {
        return openHelpLinkAndReturn(
            link = FORGOT_PASSWORD_LINK,
            expectedUrlPart = "forgot-password"
        )
    }

    fun openQuestionsAndReturn(): HelpModal {
        return openHelpLinkAndReturn(
            link = QUESTIONS_LINK,
            expectedUrlPart = "about/questions"
        )
    }

    fun close(): AuthModal {
        switchToHelpFrameIfNeeded()

        val closeButton = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToHelpFrameIfNeeded()

            driver.findElements(CLOSE_BUTTON)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        try {
            closeButton.click()
        } catch (e: Exception) {
            hoyolab.Utils.jsClick(driver, closeButton)
        }

        WebDriverWait(driver, Duration.ofSeconds(20)).until {
            !helpDialogVisibleHere()
        }

        driver.switchTo().defaultContent()

        return AuthModal(driver)
    }

    private fun openHelpLinkAndReturn(link: By, expectedUrlPart: String): HelpModal {
        switchToHelpFrameIfNeeded()

        val mainTab = driver.windowHandle
        val oldTabs = driver.windowHandles.toSet()

        val helpLink = WebDriverWait(driver, Duration.ofSeconds(30)).until {
            switchToHelpFrameIfNeeded()

            driver.findElements(link)
                .firstOrNull { element -> element.isDisplayed && element.isEnabled }
        }!!

        try {
            helpLink.click()
        } catch (e: Exception) {
            hoyolab.Utils.jsClick(driver, helpLink)
        }

        WebDriverWait(driver, Duration.ofSeconds(30)).until {
            driver.windowHandles.size > oldTabs.size ||
                    driver.currentUrl.contains(expectedUrlPart)
        }

        if (driver.windowHandles.size > oldTabs.size) {
            val newTab = driver.windowHandles.first { it !in oldTabs }

            driver.switchTo().window(newTab)

            WebDriverWait(driver, Duration.ofSeconds(30)).until {
                driver.currentUrl.contains(expectedUrlPart)
            }

            driver.close()
            driver.switchTo().window(mainTab)
        } else {
            WebDriverWait(driver, Duration.ofSeconds(30)).until {
                driver.currentUrl.contains(expectedUrlPart)
            }

            driver.navigate().back()
        }

        waitUntilVisible()

        return this
    }

    private fun helpDialogVisibleHere(): Boolean {
        return try {
            existsPresent(TITLE) ||
                    existsPresent(HELP_DIALOG) ||
                    pageHasHelpText()
        } catch (e: Exception) {
            false
        }
    }

    private fun pageHasHelpText(): Boolean =
        driver.pageSource.contains("Столкнулись с проблемами?", ignoreCase = true) ||
                driver.pageSource.contains("Забыли пароль?", ignoreCase = true) ||
                driver.pageSource.contains("Вопросы и ответы", ignoreCase = true)

    private fun existsPresent(selector: By): Boolean {
        return try {
            driver.findElements(selector).isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}