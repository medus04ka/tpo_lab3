import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

class ObjectCardTest : BaseTest() {

    @Test
    fun openCard() {
        val card = page.openFirstMarker()

        assertTrue(
            card.isVisible(),
            "После нажатия на объект должна открыться карточка объекта"
        )
    }

    @Test
    fun openReallyCard() {
        val card = page.openFirstMarker()

        assertTrue(
            card.hasDescription(),
            "В карточке объекта должны отображаться название, категория или изображение"
        )
    }
    //TODO: додумать тест
    @Test
    fun floorCard() {
        val card = page.openFirstMarker()

        assumeTrue(
            card.hasGoToFloorButton(),
            "У выбранного объекта нет кнопки перехода на соответствующий этаж"
        )

        card.goToFloor()

        assertTrue(
            page.isMapVisible(),
            "После перехода на соответствующий этаж карта должна оставаться доступной"
        )
    }
    //TODO: пока не трогала авторизованных
    @Test
    fun markedCard() {
        val card = page.openFirstMarker()

        assumeTrue(
            card.hasReceived(),
            "У выбранного объекта нет действия Получено"
        )

        card.clickReceived()

        assertTrue(
            page.pageContainsText("Войти") ||
                    page.pageContainsText("Пароль") ||
                    page.pageContainsText("учётную запись") ||
                    page.pageContainsText("Получено"),
            "После нажатия Получено должно открыться окно авторизации или измениться статус объекта"
        )
    }
}