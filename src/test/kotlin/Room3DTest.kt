import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Room3DTest : BaseTest() {

    @Test
    fun room3d() {
        page.open3DRoomMap()
        page.click3DRoomMarker()
        page.confirm3DRoomTransition()
        assertTrue(
            page.is3DRoomOpened(),
            "После подтверждения перехода должна открыться 3D-карта комнаты"
        )
        assertTrue(
            page.roomSwiperItemsCount() >= 4,
            "В 3D-режиме должно быть несколько превью комнат"
        )
        page.clickRoomPreviewByIndex(1)
        assertEquals(
            1,
            page.activeRoomIndex(),
            "После клика по второй комнатке активной должна стать вторая комнатка"
        )
        page.clickRoomPreviewByIndex(2)
        assertEquals(
            2,
            page.activeRoomIndex(),
            "После клика по третьей комнатке активной должна стать третья комнатка"
        )
        page.clickRoomPreviewByIndex(3)
        assertEquals(
            3,
            page.activeRoomIndex(),
            "После клика по четвёртой комнатке активной должна стать четвёртая комнатка"
        )
        page.clickRoomPreviewByIndex(0)
        assertEquals(
            0,
            page.activeRoomIndex(),
            "После клика по первой комнатке активной должна снова стать первая комнатка"
        )
        page.close3DRoom()
        assertFalse(
            page.is3DRoomOpened(),
            "После нажатия Закрыть 3D-карта комнаты должна закрыться"
        )
        assertTrue(
            page.isMapVisible(),
            "После закрытия 3D-комнаты обычная карта должна оставаться доступной"
        )
        page.openFloor1()
        assertEquals(
            "этаж 1",
            page.currentActiveFloor(),
            "После закрытия 3D-карты и нажатия на этаж 1 активным должен стать этаж 1"
        )
        page.openFloor2()
        assertEquals(
            "этаж 2",
            page.currentActiveFloor(),
            "После нажатия на этаж 2 активным должен стать этаж 2"
        )
        page.openDioramaModel()
        assertEquals(
            "Модель диорамы",
            page.currentActiveFloor(),
            "После нажатия на модель диорамы активной должна стать Модель диорамы"
        )
        page.openFloor1()
        page.openFloor2()
        page.openFloor1()
        page.openFloor2()
        page.openFloor1()

    }
}