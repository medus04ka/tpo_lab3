import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LocationTest : BaseTest() {
    @ParameterizedTest(name = "Location {0} should open")
    @ValueSource(
        strings = [
            "Лофу Сяньчжоу",
            "Ярило-VI",
            "Звёздный экспресс",
            "Пенакония",
            "Двумерния",
            "Космическая станция",
            "Амфореус",
            "Эфирные войны"
        ]
    )
    fun locationShouldOpen(location: String) {
        page.changeLocation(location)

        assertTrue(
            page.pageContainsText(location) ||
                    page.locationTitle().contains(location.substringBefore(" ")) ||
                    page.pageContainsText(location.substringBefore(" ")),
            "После выбора должна открыться локация $location"
        )
    }

    @Test
    fun locationPrblg3(): Unit {
        page.openIsolatedZone()
        assertTrue(page.pageContainsText("Изолированная зона"),"alvbybcnhfnbdrf")
    }

    @Test
    fun locationPrblg(): Unit {
        page.lofyAdmZone()

        assertTrue(page.pageContainsText("Административный район1"),"alvbybcnhfnbdrf")
    }

    @Test
    fun locationPrblg2(): Unit {
        page.penaconyHotel()

        assertTrue(page.pageContainsText("Золотой миг"),"alvвшывафрf")
    }

    @Test
    fun holoMap() {
        page.openHoloMapOverview()

        assertTrue(
            page.isHoloMapOverviewOpened(),
            "После нажатия на Обзор голографической карты должен открыться holo-режим"
        )

        page.waitHoloCardsLoaded()

        page.clickHoloCardAndWaitActive("Ярило-VI")

        assertTrue(
            page.isHoloCardActive("Ярило-VI"),
            "После клика активной должна стать карточка Ярило-VI"
        )

        assertTrue(
            page.hasHoloFloors(),
            "У Ярило-VI должны отображаться 1 этаж и 2 этаж"
        )

        page.clickHoloFloor1()
        page.clickHoloFloor2()

        page.clickHoloCardAndWaitActive("Пенакония (реальность)")

        assertTrue(
            page.isHoloCardActive("Пенакония (реальность)"),
            "После клика активной должна стать карточка Пенакония (реальность)"
        )

        assertTrue(
            page.hasHoloFloors(),
            "У Пенаконии должны отображаться 1 этаж и 2 этаж"
        )

        page.clickHoloFloor1()
        page.clickHoloFloor2()

        page.closeHoloMapOverview()

        assertTrue(
            page.isHoloMapOverviewClosed(),
            "После нажатия Назад должен открыться обычный режим карты"
        )
    }
}