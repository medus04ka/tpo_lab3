import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ObjectCardTest : BaseTest() {

    @Test
    fun openCard() {
        page.openIsolatedZone()
        val card = page.openFirstMarker()
        assertTrue(
            card.isVisible(),
            "После нажатия на объект должна открыться карточка объекта"
        )
    }

    @Test
    fun openReallyCard() {
        page.openIsolatedZone()
        val card = page.openFirstMarker()
        assertTrue(
            card.hasDescription(),
            "В карточке объекта должны отображаться название и категория"
        )
        assertTrue(
            card.hasImage(),
            "В карточке объекта должно отображаться изображение"
        )
    }

    @Test
    fun floorCard() {
        page.openIsolatedZone()
        val card = page.openFirstMarker()
        assertTrue(
            card.hasGoToFloorButton(),
            "У выбранного объекта должна быть кнопка перехода на соответствующий этаж"
        )
        card.goToFloor()
        assertTrue(
            page.isMapVisible(),
            "После перехода на соответствующий этаж карта должна оставаться доступной"
        )
    }

    @Test
    fun markedCard() {
        page.openIsolatedZone()
        val card = page.openReceivedObjectMarker()
        card.clickReceived()
        val auth = AuthModal(driver)
        assertTrue(
            auth.isVisible(),
            "После нажатия Получено должно открыться окно авторизации или измениться статус объекта"
        )
    }
}