package pair

import api.pair.Pair
import junit.framework.TestCase
import junit.framework.TestCase.assertTrue
import org.junit.Test

abstract class PairBaseTest<K, T> {
    @Test
    fun `create pair`() {
        createPair()
    }

    @Test
    fun `pair equals`() {
        assertTrue(createPair() == createPair())
    }

    @Test
    fun `pair toString`() {
        assertTrue(createPair().toString() == createPair().toString())
    }

    @Test
    fun `pairs refs shouldn't be equal`() {
        assertTrue(createPair() !== createPair())
    }

    protected abstract fun getFirstElement(): K

    protected abstract fun getSecondElement(): T

    protected abstract fun createPairImpl(first: K, second: T): Pair<K, T>

    private fun createPair(first: K = getFirstElement(), second: T = getSecondElement()): Pair<K, T> =
        createPairImpl(first, second).also {
            TestCase.assertEquals(first, first)
            TestCase.assertEquals(second, second)
        }
}