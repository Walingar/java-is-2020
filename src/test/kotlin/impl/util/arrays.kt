package impl.util

import junit.framework.TestCase.assertEquals

internal fun <T> assert2DArraysEqual(
    expected: Array<out Array<T>>,
    actual: Array<out Array<T>>,
    message: (Int, Int) -> String
) {
    expected.forEachIndexed { i, row ->
        row.forEachIndexed { j, el1 ->
            assertEquals(message(i, j), el1, actual[i][j])
        }
    }
}