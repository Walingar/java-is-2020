package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.HashSet;
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
        HashSet<Integer> discoveredUsers = new HashSet<>();

        return calculateFollowersRecursiveAsync(id, depth, predicate, discoveredUsers);
    }

    private CompletableFuture<Integer> calculateFollowersRecursiveAsync(int userId, int currentDepth,
                                                                        Predicate<UserInfo> condition,
                                                                        HashSet<Integer> discoveredUsers) {
        if (!discoveredUsers.add(userId)) {
            return CompletableFuture.completedFuture(0);
        }

        //Check current user (root included)
        CompletableFuture<Integer> currentUserIsFollower = isUserFollow(userId, condition);

        if (currentDepth == 0) {
            return currentUserIsFollower;
        }

        return visitChildNodes(userId, currentDepth - 1, condition, discoveredUsers, currentUserIsFollower);
    }

    private CompletableFuture<Integer> visitChildNodes(int userId, int depth, Predicate<UserInfo> condition,
                                                       HashSet<Integer> discoveredUsers,
                                                       CompletableFuture<Integer> currentUserIsFollower) {
        return network.getFollowers(userId).
                thenCompose(followers -> followers.stream().
                        map(followerId -> calculateFollowersRecursiveAsync(followerId, depth, condition, discoveredUsers)).
                        reduce((first, second) -> first.thenCombine(second, Integer::sum)).
                        orElse(CompletableFuture.completedFuture(0)).
                        thenCombine(currentUserIsFollower, Integer::sum));
    }

    private CompletableFuture<Integer> isUserFollow(int userId, Predicate<UserInfo> condition) {
        return network.getUserInfo(userId).thenApply(userInfo -> condition.test(userInfo) ? 1 : 0);
    }
}
