package network

import impl.network.FollowersStatsFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.lang.Thread.sleep
import kotlin.system.measureNanoTime

internal class FollowersStatsTest {
    private val graph = mapOf(
        1 to listOf(2, 3, 4),
        2 to listOf(1, 3, 5, 6, 8),
        3 to listOf(15),
        4 to listOf(12),
        5 to listOf(),
        6 to listOf(3, 5, 15),
        8 to listOf(),
        12 to listOf(),
        15 to listOf(6)
    )

    private val info = mapOf(
        1 to UserInfoImpl("Ivan", "Ivanov", 10),
        2 to UserInfoImpl("Nikolay", "Petrov", 12),
        3 to UserInfoImpl("Sergey", "Dmitriev", 18),
        4 to UserInfoImpl("Dmitry", "Belyaev", 25),
        5 to UserInfoImpl("Vadim", "Smirnov", 21),
        6 to UserInfoImpl("Lev", "Fedorov", 19),
        8 to UserInfoImpl("Yaroslav", "Dzuba", 8),
        12 to UserInfoImpl("Ilya", "Kokorin", 10),
        15 to UserInfoImpl("Alexander", "Tester", 55)
    )

    private val cores = Runtime.getRuntime().availableProcessors()

    private fun getNetwork(threads: Int) = SocialNetworkImpl(threads, graph, info)

    @Test
    fun `test depth 1 thread 1`() {
        val network = getNetwork(1)
        val followersStats = FollowersStatsFactory.getInstance(network)
        val result = followersStats.followersCountBy(1, 1) {
            it.age > 12
        }.get()
        assertEquals(2, result)
    }

    @Test
    fun `test max depth thread 1`() {
        val network = getNetwork(1)
        val followersStats = FollowersStatsFactory.getInstance(network)
        val result = followersStats.followersCountBy(1, 10) {
            it.age > 12
        }.get()
        assertEquals(5, result)
    }

    @Test
    fun `test depth 1 thread max`() {
        val network = getNetwork(cores)
        val followersStats = FollowersStatsFactory.getInstance(network)
        val result = followersStats.followersCountBy(1, 1) {
            it.age > 12
        }.get()
        assertEquals(2, result)
    }

    @Test
    fun `test max depth thread max`() {
        val network = getNetwork(cores)
        val followersStats = FollowersStatsFactory.getInstance(network)
        val result = followersStats.followersCountBy(1, 10) {
            it.age > 12
        }.get()
        assertEquals(5, result)
    }

    @Test
    fun `test non-max depth thread max`() {
        val network = getNetwork(cores)
        val followersStats = FollowersStatsFactory.getInstance(network)
        val result = followersStats.followersCountBy(3, 3) {
            it.age > 20
        }.get()
        assertEquals(2, result)
    }

    @Test
    fun `test include root`() {
        val network = getNetwork(cores)
        val followersStats = FollowersStatsFactory.getInstance(network)
        val result = followersStats.followersCountBy(3, 3) {
            it.age > 5
        }.get()
        assertEquals(4, result)
    }

    @Test
    fun `test 2 threads should be faster than 1 thread`() {
        val oneThreadTime = measureNanoTime {
            val network = getNetwork(1)
            val followersStats = FollowersStatsFactory.getInstance(network)
            followersStats.followersCountBy(1, 10) {
                sleep(1000)
                it.age > 12
            }.get()
        }
        val twoThreadTime = measureNanoTime {
            val network = getNetwork(2)
            val followersStats = FollowersStatsFactory.getInstance(network)
            followersStats.followersCountBy(1, 10) {
                sleep(1000)
                it.age > 12
            }.get()
        }
        assertTrue(
            "One thread time: $oneThreadTime. Two thread time: $twoThreadTime",
            oneThreadTime.toDouble() / twoThreadTime > 1.3
        )
    }
}