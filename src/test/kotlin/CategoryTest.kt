import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CategoryTest : BaseTest() {
//TODO: дописать исключения (мне лень)
    @ParameterizedTest(name = "Category {0} should be visible")
    @ValueSource(
        strings = [
            "Книги",
            "Противники",
            "Точки интереса",
            "Видимые сундуки",
            "Фрагментум",
            "Сундук-головоломка",
            "Событие"
        ]
    )
    fun categoryIsReal(category: String) {
        assertTrue(
            page.isCategoryVisible(category),
            "В боковой панели должна отображаться категория $category"
        )
    }

    @ParameterizedTest(name = "Category {0} should be clickable")
    @ValueSource(
        strings = [
            "Книги",
            "Точки интереса",
            "Противники"
        ]
    )
    //TODO: хуйня тест переделать думаю
    fun lalalal(category: String) {
        page.clickCategory(category)

        assertTrue(
            page.isMapVisible(),
            "После выбора категории карта должна оставаться доступной"
        )
    }
}