import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class ZoomTest : BaseTest() {
    //TODO: дописать (мне лень)
    @Test
    fun zoooom() {
        val before = page.zoomSliderStyle()

        page.moveZoomSlider()

        val after = page.zoomSliderStyle()

        assertNotEquals(
            before,
            after,
            "При перемещении ползунка масштаб карты должен измениться"
        )
    }
}