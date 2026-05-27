import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.FindBy

class AccountTest : BaseTest() {

    @Test
    fun fastAuthentication() {
        val auth = page.openAuthWindow()
        auth.loginAsTestUser()
        page.waitUntilLoggedIn()
        assertTrue(
            page.isLoggedIn(),
            "После успешного входа пользователь должен быть авторизован"
        )
        page.openIsolatedZone()
        page.openReceivedObjectMarker2()
        page.markCurrentObjectAsReceived()
        page.openLoggedInUserMenu()
        assertTrue(
            page.hasAccountItem(),
            "В меню авторизованного пользователя должен быть пункт Учётная запись"
        )
        assertTrue(
            page.hasChangeCharacterItem(),
            "В меню авторизованного пользователя должен быть пункт Сменить персонажа"
        )
        assertTrue(
            page.hasLogoutItem(),
            "В меню авторизованного пользователя должен быть пункт Выйти"
        )
        page.clickAccountItem()
        assertTrue(
            driver.currentUrl.contains("hoyolab.com") ||
                    driver.currentUrl.contains("accountCenter"),
            "Пункт Учётная запись должен открывать страницу аккаунта HoYoLAB"
        )
        page.closeCurrentTabAndReturnToMap()
        page.openLoggedInUserMenu()
        page.clickChangeCharacterItem()
        assertTrue(
            page.isCharacterSelectorVisible(),
            "После нажатия Сменить персонажа должно открыться окно Выбор персонажа"
        )
        assertTrue(
            page.hasCharacterServerSelector(),
            "В окне выбора персонажа должен отображаться выбор сервера"
        )
        page.openCharacterServerDropdown()
        page.selectEuropeServer()
        page.confirmCharacterSelection()
        assertTrue(
            page.isMapVisible(),
            "После подтверждения выбора персонажа карта должна оставаться доступной"
        )
        page.openLoggedInUserMenu()
        assertTrue(
            page.hasLogoutItem(),
            "Перед выходом в меню должен быть пункт Выйти"
        )
        page.clickLogoutItem()
        val authAfterLogout = page.openAuthWindow()
        assertTrue(
            authAfterLogout.isVisible(),
            "После выхода из аккаунта при нажатии на кнопку пользователя должна открываться форма входа"
        )
    }

    @Tag("manual")
    @Test
    fun registration() {
        val auth = page.openAuthWindow()

        val registration = auth.openRegistration()

        registration.registerWithManualCode(
            email = "pvc.cvp@mail.com",
            password = "Pipisya227!"
        )
    }

    @Test
    fun help() {
        val auth = page.openAuthWindow()

        val help = auth.openHelp()

        assertTrue(
            help.isVisible(),
            "После клика по 'Столкнулись с проблемами?' должна открыться панель помощи"
        )

        assertTrue(
            help.hasForgotPasswordButton(),
            "В help-панельке должна быть кнопка 'Забыли пароль?'"
        )

        assertTrue(
            help.hasQuestionsButton(),
            "В help-панельке должна быть кнопка 'Вопросы и ответы'"
        )

        help.openForgotPasswordAndReturn()

        assertTrue(
            help.isVisible(),
            "После проверки перехода 'Забыли пароль?' нужно вернуться к help-панельке"
        )

        auth.openHelp()

        help.openQuestionsAndReturn()

        assertTrue(
            help.isVisible(),
            "После проверки перехода 'Вопросы и ответы' нужно вернуться к help-панельке"
        )

        help.close()
    }
}