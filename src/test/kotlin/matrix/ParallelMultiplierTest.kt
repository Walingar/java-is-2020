package matrix

import impl.matrix.ParallelMultiplierFactory
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ParallelMultiplierTest {
    companion object {
        private const val EPS = 1e-5
    }

    private fun checkResult(expected: Array<DoubleArray>, actual: Array<DoubleArray>) {
        expected.forEachIndexed { rowIndex, doubles ->
            doubles.forEachIndexed { index, d ->
                assertEquals("Indices: [$rowIndex, $index]", d, actual[rowIndex][index], EPS)
            }
        }
    }

    @Test
    fun `check matrix square`() {
        val initialData = arrayOf(
            doubleArrayOf(0.0, 1.0),
            doubleArrayOf(2.0, 3.0)
        )
        val multiplier = ParallelMultiplierFactory.getInstance(Runtime.getRuntime().availableProcessors())
        val result = multiplier.mul(initialData, initialData)
        val expected = arrayOf(
            doubleArrayOf(2.0, 3.0),
            doubleArrayOf(6.0, 11.0)
        )
        checkResult(expected, result)
    }

    @Test
    fun `check matrix multiplication`() {
        val a = arrayOf(
            doubleArrayOf(0.0, 1.0),
            doubleArrayOf(2.0, 3.0),
            doubleArrayOf(15.0, -3.0)
        )
        val b = arrayOf(
            doubleArrayOf(0.0, 1.0, -3.0),
            doubleArrayOf(2.0, 3.0, -24.0)
        )
        val multiplier = ParallelMultiplierFactory.getInstance(Runtime.getRuntime().availableProcessors())
        val result = multiplier.mul(a, b)
        val expected = arrayOf(
            doubleArrayOf(2.0, 3.0, -24.0),
            doubleArrayOf(6.0, 11.0, -78.0),
            doubleArrayOf(-6.0, 6.0, 27.0)
        )
        checkResult(expected, result)
    }
}