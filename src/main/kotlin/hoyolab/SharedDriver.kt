package hoyolab

import org.openqa.selenium.WebDriver

object SharedDriver {

    private var driver: WebDriver? = null

    @Synchronized
    fun getDriver(): WebDriver {
        if (driver == null) {
            driver = Utils.getDriver()

            Runtime.getRuntime().addShutdownHook(
                Thread {
                    quitDriver()
                }
            )
        }

        return driver!!
    }

    @Synchronized
    fun quitDriver() {
        try {
            driver?.quit()
        } catch (e: Exception) {
        } finally {
            driver = null
        }
    }
}