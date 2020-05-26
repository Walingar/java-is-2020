package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {
    SocialNetwork network;

    FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return followersCountBy(id, depth, predicate, new ConcurrentSkipListSet<>() {{
            add(id);
        }});
    }

    private CompletableFuture<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate,
                                                        ConcurrentSkipListSet<Integer> visited) {
        // check if current user matches the predicate
        var user = network.getUserInfo(id)
                .thenApply(userInfo -> predicate.test(userInfo) ? 1 : 0);

        if (depth == 0) {
            return user;
        }

        return user.thenCombine(
                network.getFollowers(id)
                        .thenCompose(
                                followers -> followers.stream()
                                        .map(
                                                follower -> {
                                                    if (visited.add(follower)) {
                                                        // count followers for unvisited follower
                                                        return followersCountBy(follower, depth - 1,
                                                                predicate, visited);
                                                    } else {
                                                        // we have already visited this follower, return 0
                                                        return CompletableFuture.completedFuture(0);
                                                    }
                                                })
                                        .reduce((a, b) -> a.thenCombine(b, Integer::sum))
                                        .orElse(CompletableFuture.completedFuture(0))),
                Integer::sum);
    }
}
