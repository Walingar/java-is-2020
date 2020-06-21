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

    public FollowersStatsImpl(SocialNetwork network){
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return count(id, depth, predicate,
                new ConcurrentHashMap<>() {{
                    put(id, true);
                }}
        );
    }

    private CompletableFuture<Integer> count(int id, int depth, Predicate<UserInfo> predicate, ConcurrentHashMap<Integer, Boolean> visitMap){
        CompletableFuture<Integer> userCountFuture = network.getUserInfo(id)
                .thenApply(
                        (userInfo) -> predicate.test(userInfo) ? 1 : 0
                );
        if (depth == 0) return userCountFuture;

        return network.getFollowers(id)
                .thenCompose((followers) -> followers
                        .stream()
                        .map(
                                follower -> {
                                    if (Objects.nonNull(visitMap.putIfAbsent(follower, true))) {
                                        return CompletableFuture.completedFuture(0);
                                    }
                                    return count(follower, depth - 1, predicate, visitMap);
                                })
                        .reduce(CompletableFuture.completedFuture(0), (subtotalFuture, additionFuture) -> subtotalFuture.thenCombine(additionFuture, Integer::sum))
                )
                .thenCombine(userCountFuture, Integer::sum);
    }
}
