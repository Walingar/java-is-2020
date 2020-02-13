package impl.image

import impl.util.assert2DArraysEqual
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.awt.Color

internal class ImageConverterTest {
    companion object {
        private val RGB = arrayOf(
            intArrayOf(-15985343, -16773949),
            intArrayOf(-16742651, -16771780),
            intArrayOf(-12409120, -16765162)
        )
        private val COLORS = arrayOf(
            arrayOf(Color(12, 21, 65, 255), Color(0, 12, 195, 255)),
            arrayOf(Color(0, 135, 5, 255), Color(0, 21, 60, 255)),
            arrayOf(Color(66, 166, 224, 255), Color(0, 47, 22, 255))
        )
    }

    private fun getIncorrectValueOnPositionMessage(value: String, x: Int, y: Int) =
        "Incorrect $value on position [x:$x, y:$y]"

    @Test
    fun convertToColor() {
        val converter = ImageConverterFactory.getInstance()
        assert2DArraysEqual(converter.convertToColor(RGB), COLORS) { i, j ->
            getIncorrectValueOnPositionMessage("color", j, i)
        }
    }

    @Test
    fun convertToRgb() {
        val converter = ImageConverterFactory.getInstance()
        converter.convertToRgb(COLORS).forEachIndexed { i, row ->
            row.forEachIndexed { j, actual ->
                assertEquals(getIncorrectValueOnPositionMessage("rgb", j, i), actual, RGB[i][j])
            }
        }
    }
}