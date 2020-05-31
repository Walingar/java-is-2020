package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public final class FollowersStatsImpl implements FollowersStats {
    private final SocialNetwork network;

    public FollowersStatsImpl(final SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(final int id, final int depth, final Predicate<UserInfo> predicate) {
        return followersCountBy(id, depth, predicate, ConcurrentHashMap.newKeySet());
    }

    private CompletableFuture<Integer> followersCountBy(final int id, final int depth, final Predicate<UserInfo> predicate, final Set<Integer> visited) {
        if (!visited.add(id)) {
            return CompletableFuture.completedFuture(0);
        }
        CompletableFuture<Integer> currentUserMatch = network.getUserInfo(id).thenApply(info -> predicate.test(info) ? 1 : 0);
        if (depth == 0) {
            return currentUserMatch;
        }
        return network.getFollowers(id).thenCompose(
                followers -> followers.stream()
                        .map(follower -> followersCountBy(follower, depth - 1, predicate, visited))
                        .reduce((c1, c2) -> c1.thenCombine(c2, Integer::sum))
                        .orElse(CompletableFuture.completedFuture(0))
                        .thenCombine(currentUserMatch, Integer::sum));
    }
}
