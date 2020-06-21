package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Set;
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
        return doFollowersCountBy(id, depth, predicate, ConcurrentHashMap.newKeySet());
    }

    private CompletableFuture<Integer> doFollowersCountBy(int currentId, int currentDepth,
                                                          Predicate<UserInfo> predicate, Set<Integer> usedUsers) {
        if (!usedUsers.add(currentId)) {
            return CompletableFuture.completedFuture(0);
        }

        var currentUser = network.getUserInfo(currentId).thenApply(userInfo -> predicate.test(userInfo) ? 1 : 0);
        if (currentDepth == 0) {
            return currentUser;
        }

        return network.getFollowers(currentId)
                .thenCompose(followers -> followers.stream()
                        .map(followerId -> doFollowersCountBy(followerId, currentDepth - 1, predicate, usedUsers))
                        .reduce((f1, f2) -> f1.thenCombine(f2, Integer::sum))
                        .orElse(CompletableFuture.completedFuture(0)))
                .thenCombine(currentUser, Integer::sum);
    }
}
