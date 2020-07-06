package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {
    private final SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return count(id, depth, predicate,
                new CopyOnWriteArraySet<>() {{
                    add(id);
                }}
        );
    }

    private CompletableFuture<Integer> count(int id, int depth, Predicate<UserInfo> predicate, Set<Integer> visitSet) {
        CompletableFuture<Integer> userCountFuture = network.getUserInfo(id)
                .thenApply((userInfo) -> predicate.test(userInfo) ? 1 : 0);
        if (depth == 0) {
            return userCountFuture;
        }

        return network.getFollowers(id)
                .thenCompose((followers) -> followers
                        .stream()
                        .map(follower -> {
                                    if (visitSet.contains(follower)) {
                                        return CompletableFuture.completedFuture(0);
                                    }
                                    visitSet.add(follower);
                                    return count(follower, depth - 1, predicate, visitSet);
                                })
                        .reduce(CompletableFuture.completedFuture(0),
                                (subtotalFuture, additionFuture) -> subtotalFuture.thenCombine(additionFuture, Integer::sum))
                )
                .thenCombine(userCountFuture, Integer::sum);
    }
}
