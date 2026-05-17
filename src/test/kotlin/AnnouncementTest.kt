import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AnnouncementTest : BaseTest() {

    @Test
    fun closeOK() {
        val announcement = page.openAnnouncements()

        assertTrue(
            announcement.isVisible(),
            "Журнал обновлений должен открыться"
        )

        announcement.close()

        assertTrue(
            page.isMapVisible(),
            "После закрытия журнала обновлений карта должна быть доступна"
        )
    }
}