package impl.image

import impl.image.ImageUtil.readImage
import impl.util.assert2DArraysEqual
import org.junit.Test
import java.awt.Color
import java.io.File

internal class ConvolutionProviderTest {
    companion object {
        private val GAUSSIAN_KERNEL = arrayOf(
            doubleArrayOf(1.0 / 16, 1.0 / 8, 1.0 / 16),
            doubleArrayOf(1.0 / 8, 1.0 / 4, 1.0 / 8),
            doubleArrayOf(1.0 / 16, 1.0 / 8, 1.0 / 16)
        )

        private val IDENTITY_KERNEL = arrayOf(
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0)
        )

        private val IMAGE_FILE_NAMES = listOf("pic1.png", "pic2.png")
    }

    private fun testConvolution(
        originImage: Array<out Array<Color>>,
        expectedImage: Array<out Array<Color>>,
        kernel: Array<out DoubleArray>,
        message: (Int, Int) -> String
    ) {
        val convolution = ConvolutionProviderFactory.getInstance().apply(originImage, kernel)
        assert2DArraysEqual(expectedImage, convolution, message)
    }

    private fun loadOriginImage(fileName: String): Array<out Array<Color>> =
        readImage(File("resources/image/origin/$fileName"))

    private fun loadBlurImage(fileName: String): Array<out Array<Color>> =
        readImage(File("resources/image/blur/$fileName"))

    private fun getIncorrectPixelErrorMessage(fileName: String, kernelName: String, x: Int, y: Int) =
        "Incorrect pixel [x:$x, y:$y] after convolution of $fileName using $kernelName kernel"

    @Test
    fun identity() {
        IMAGE_FILE_NAMES.forEach { fileName ->
            testConvolution(loadOriginImage(fileName), loadOriginImage(fileName), IDENTITY_KERNEL) { i, j ->
                getIncorrectPixelErrorMessage(fileName, "identity", j, i)
            }
        }
    }

    @Test
    fun blur() {
        IMAGE_FILE_NAMES.forEach { fileName ->
            testConvolution(loadOriginImage(fileName), loadBlurImage(fileName), GAUSSIAN_KERNEL) { i, j ->
                getIncorrectPixelErrorMessage(fileName, "gaussian blur", j, i)
            }
        }
    }
}