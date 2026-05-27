import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class ZoomTest : BaseTest() {
    @Test
    fun zoooom() {
        val beforeZoom = page.currentZoomState()
        page.zoomIn()
        val afterZoomIn = page.currentZoomState()
        assertNotEquals(
            beforeZoom,
            afterZoomIn,
            "После нажатия на плюс состояние масштаба карты должно измениться"
        )
        page.zoomOut()
        val afterZoomOut = page.currentZoomState()
        assertNotEquals(
            afterZoomIn,
            afterZoomOut,
            "После нажатия на минус состояние масштаба карты должно измениться"
        )
        page.zoomOut()
        page.zoomOut()
        page.zoomIn()
        page.zoomIn()
    }
}