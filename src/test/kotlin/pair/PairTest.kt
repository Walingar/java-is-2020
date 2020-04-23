package pair

import api.pair.Pair
import impl.pair.PairFactory


class PairTest : PairBaseTest<Map<Int, Int>, String>() {
    override fun getFirstElement(): Map<Int, Int> = mapOf(1 to 2, 3 to 4)

    override fun getSecondElement(): String = "Тестики"

    override fun createPairImpl(first: Map<Int, Int>, second: String): Pair<Map<Int, Int>, String> =
        PairFactory.of(first, second)
}