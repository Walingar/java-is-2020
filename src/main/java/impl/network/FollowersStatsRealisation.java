package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public final class FollowersStatsRealisation implements FollowersStats {
    private SocialNetwork network;

    public FollowersStatsRealisation(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return countFollowers(id, depth, predicate, ConcurrentHashMap.newKeySet());
    }

    private CompletableFuture<Integer> countFollowers(int id, int depth, Predicate<UserInfo> predicate,  Set<Integer> visited) {
        if (visited.add(id)) {

            if (depth == 0) {
                return network.getUserInfo(id)
                        .thenApply(userInfo -> predicate.test(userInfo) ? 1 : 0);
            }

            return network.getFollowers(id)
                    .thenCompose(studentIds -> studentIds.stream()
                            .map(student -> countFollowers(student, depth - 1, predicate, visited))
                            .reduce((set1, set2) -> set1.thenCombine(set2, Integer::sum))
                            .orElse(CompletableFuture.completedFuture(0))
                            .thenCombine(network.getUserInfo(id)
                                    .thenApply(userInfo -> predicate.test(userInfo) ? 1 : 0), Integer::sum));
        }

        return CompletableFuture.completedFuture(0);
    };
}