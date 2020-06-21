package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {

    private final SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return doCountFollowersBy(id, depth, predicate, new ConcurrentHashMap<>() {{ put(id, false); }} );
    }

    private CompletableFuture<Integer> doCountFollowersBy(int id, int depth, Predicate<UserInfo> predicate, ConcurrentHashMap<Integer, Boolean> visited) {
        var userCountFuture = network.getUserInfo(id)
                .thenApply((userInfo) -> predicate.test(userInfo) ? 1 : 0);

        if (depth == 0) return userCountFuture;

        return network.getFollowers(id)
                .thenCompose((followers) -> followers
                        .stream()
                        .map(follower -> {
                            if (Objects.nonNull(visited.putIfAbsent(follower, true))) {
                                return CompletableFuture.completedFuture(0);
                            }
                            return doCountFollowersBy(follower, depth - 1, predicate, visited);
                        })
                        .reduce(CompletableFuture.completedFuture(0), (l, r) -> l.thenCombine(r, Integer::sum)))
                .thenCombine(userCountFuture, Integer::sum);
    }
}
