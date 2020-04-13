package pair

import api.pair.Pair
import impl.pair.NumberPairFactory

class NumberPairTest : PairBaseTest<Int, Long>() {
    override fun getFirstElement(): Int = 123

    override fun getSecondElement() = 2L

    override fun createPairImpl(first: Int, second: Long): Pair<Int, Long> = NumberPairFactory.of(first, second)
}