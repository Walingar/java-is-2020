package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {
    private SocialNetwork network;

    private Map<Integer, UserInfo> userInfoCache = new ConcurrentHashMap<>();
    private Map<Integer, Collection<Integer>> followersCache = new ConcurrentHashMap<>();

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return CompletableFuture.completedFuture(
                doCountFollowersBy(id, depth, predicate,
                        new ConcurrentHashMap<>() {{
                            put(id, id);
                        }}
                ));
    }

    private Integer doCountFollowersBy(int id, int depth, Predicate<UserInfo> predicate, ConcurrentHashMap<Integer, Integer> visited) {
        try {
            var userInfo = getUserInfo(id);

            var thisUserValue = predicate.test(userInfo) ? 1 : 0;

            if (depth == 0) {
                return thisUserValue;
            }

            var followersFutures = new ArrayList<Future<Integer>>();
            for (var followerId : getUserFollowers(id)) {

                if (visited.putIfAbsent(followerId, followerId) != null) {
                    continue;
                }
                followersFutures.add(
                        CompletableFuture.supplyAsync(() -> doCountFollowersBy(followerId, depth - 1, predicate, visited))
                );
            }

            for (var future : followersFutures) {
                thisUserValue += future.get();
            }

            return thisUserValue;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(String.format("Something went wrong: %s", e.getMessage()));
            return null;
        }
    }

    private UserInfo getUserInfo(int id) {
        return userInfoCache.computeIfAbsent(id, (k) -> {
            try {
                return network.getUserInfo(id).get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println(String.format("Something went wrong: %s", e.getMessage()));
                return null; // this way we will not add anything to the map
            }
        });
    }

    private Collection<Integer> getUserFollowers(int id) {
        return followersCache.computeIfAbsent(id,
                (k) -> {
                    try {
                        return network.getFollowers(id).get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.err.println(String.format("Something went wrong: %s", e.getMessage()));
                        return null; // this way we will not add anything to the map
                    }
                });
    }

}
