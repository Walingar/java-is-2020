package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {

    private final SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return followersCountBy(id, depth, predicate, new HashSet<>());
    }

    private CompletableFuture<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate, Set<Integer> isVisited) {
        if (!isVisited.add(id)) {
            return CompletableFuture.completedFuture(0);
        }
        CompletableFuture<Integer> currentUserCompletableFeature = network.getUserInfo(id).thenApply(userInfo -> predicate.test(userInfo) ? 1 : 0);

        if (depth == 0) {
            return currentUserCompletableFeature;
        }

        return network.getFollowers(id)
                .thenCompose(followers -> followers.stream()
                        .map(followerId -> followersCountBy(followerId, depth - 1, predicate, isVisited))
                        .reduce((a, b) -> a.thenCombine(b, Integer::sum))
                        .orElse(CompletableFuture.completedFuture(0))
                        .thenCombine(currentUserCompletableFeature, Integer::sum));
    }
}
