import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CategoryTest : BaseTest() {
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
        prepareMapForCategory(category)
        assertTrue(
            page.isCategoryVisible(category),
            "В боковой панели должна отображаться категория $category"
        )
    }
    @ParameterizedTest(name = "Category {0} should be clickable")
    @ValueSource(
        strings = [
            "Книги",
            "Точки интереса"
        ]
    )
    fun categoryShouldBeClickable(category: String) {
        prepareMapForCategory(category)
        assertTrue(
            page.isCategoryVisible(category),
            "Перед нажатием категория $category должна быть доступна"
        )
        page.clickCategory(category)
        assertTrue(
            page.isMapVisible(),
            "После выбора категории карта должна оставаться доступной"
        )
    }

    private fun prepareMapForCategory(category: String) {
        when (category) {
            "Противники",
            "Видимые сундуки" -> {
                page.changeZone("Эфирные войны")
            }
            "Фрагментум",
            "Сундук-головоломка" -> {
                page.openBaseZoneDirectly()
            }
        }
    }
}