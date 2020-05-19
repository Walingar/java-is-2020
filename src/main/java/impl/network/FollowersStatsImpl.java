package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.concurrent.*;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {
    private SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return doCountFollowersBy(id, depth, predicate,
                new ConcurrentHashMap<>() {{
                    put(id, id);
                }}
        );
    }

    private CompletableFuture<Integer> doCountFollowersBy(int id, int depth, Predicate<UserInfo> predicate, ConcurrentHashMap<Integer, Integer> visited) {
        var userCountFuture = network.getUserInfo(id)
                .thenApply(
                        (userInfo) -> predicate.test(userInfo) ? 1 : 0
                );
        if (depth == 0) return userCountFuture;

        return network.getFollowers(id)
                .thenCompose(
                        (followers) ->
                                followers.stream()
                                        .map(
                                                follower -> {
                                                    if (visited.putIfAbsent(follower, follower) != null) {
                                                        return CompletableFuture.completedFuture(0);
                                                    }
                                                    return doCountFollowersBy(follower, depth - 1, predicate, visited);
                                                })
                                        .reduce((l, r) -> l.thenCombine(r, Integer::sum))
                                        .orElse(CompletableFuture.completedFuture(0)))
                .thenCombine(userCountFuture, Integer::sum);
    }
}
