package network

import api.network.SocialNetwork
import api.network.UserInfo
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.function.Supplier
import kotlin.random.Random

internal class SocialNetworkImpl(
    threads: Int,
    private val followers: Map<Int, List<Int>>,
    private val info: Map<Int, UserInfo>
) : SocialNetwork {
    private val executor = Executors.newFixedThreadPool(threads)
    private val visitedUserInfo = ConcurrentHashMap<Int, Boolean>()
    private val visitedFollowers = ConcurrentHashMap<Int, Boolean>()

    private fun <T> executeWithDelay(f: () -> T) = CompletableFuture.supplyAsync(Supplier {
        sleep(Random.nextLong(100, 400))
        f()
    }, executor)

    override fun getUserInfo(id: Int): CompletableFuture<UserInfo> = executeWithDelay {
        val isVisited = visitedUserInfo.putIfAbsent(id, true)
        check(isVisited == null) {
            "Couldn't load user info twice for $id"
        }
        info.getValue(id)
    }

    override fun getFollowers(id: Int): CompletableFuture<Collection<Int>> = executeWithDelay {
        val isVisited = visitedFollowers.putIfAbsent(id, true)
        check(isVisited == null) {
            "Couldn't load followers twice for $id"
        }
        followers[id] ?: listOf()
    }
}