import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LocationTest : BaseTest() {

    //TODO: побольше времени

    @ParameterizedTest(name = "Location {0} should open")
    @ValueSource(
        strings = [
            "Лофу Сяньчжоу",
            "Ярило-VI",
            "Звёздный экспресс",
            "Пенакония",
            "Двумерия",
            "Космическая станция",
            "Амфореус",
            "Эфирные войны"
        ]
    )
    fun locationResearch(location: String) {
        page.changeLocation(location)

        assertTrue(
            page.pageContainsText(location) ||
                    page.locationTitle().contains(location.substringBefore(" ")),
            "После выбора должна открыться локация $location"
        )
    }
}