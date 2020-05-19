package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {
    private SocialNetwork network;

    private Map<Integer, UserInfo> userInfoCache = new ConcurrentHashMap<>();
    private Map<Integer, Collection<Integer>> followersCache = new ConcurrentHashMap<>();
    private Map<Integer, Integer> userIdLocks = new ConcurrentHashMap<>();

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
//        if (depth == 0) return CompletableFuture.completedFuture(0); //Wow, this is ugly
        try {
            return doCountFollowersBY(id, depth, predicate, new ArrayList<>());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(String.format("Something went wrong during followers count (depth %d): %s", depth, e.getMessage()));
            return null;
        }
    }

    private Future<Integer> doCountFollowersBY(int id, int depth, Predicate<UserInfo> predicate, List<Integer> visited)
            throws ExecutionException, InterruptedException {
        var thisUserValue = 0;
        synchronized (getCacheSyncObject(id)) {
            if (!visited.contains(id)) { // Or we can do this in the loop below, dunno what is more performant, dunno what is safer
                var userInfo = getUserInfo(id);
                thisUserValue = predicate.test(userInfo) ? 1 : 0;
                visited.add(id);
            }
        }
        if (depth == 0) {
            return CompletableFuture.completedFuture(thisUserValue);
        }

        var followersFutures = new ArrayList<Future<Integer>>();
        for (var followerID : getUserFollowers(id)) {
            // We can double check if we already visited follower here
            followersFutures.add(doCountFollowersBY(followerID, depth - 1, predicate, visited));
        }

        for (var future: followersFutures){
            thisUserValue += future.get();
        }


        return CompletableFuture.completedFuture(thisUserValue);
    }

    private Object getCacheSyncObject(final Integer id) {
//        Here we store the very first autoboxed Integer as an object to sync around
        userIdLocks.putIfAbsent(id, id);
        return userIdLocks.get(id);
    }

    private UserInfo getUserInfo(int id) throws ExecutionException, InterruptedException {
        // Ok, here for each id we try to get some object to synchronize around, we store those in separate concurrentMap
        // We use (autoboxed)Integer as map key, but since checking for already being there is based on equals()
        //    we will not create separate locks for different boxed Integer instances.
        synchronized (getCacheSyncObject(id)) {
            if (!userInfoCache.containsKey(id)) {
                userInfoCache.put(id, network.getUserInfo(id).get());
            }
        }
        return userInfoCache.get(id);
    }

    private Collection<Integer> getUserFollowers(int id) throws ExecutionException, InterruptedException {
        synchronized (getCacheSyncObject(id)) {
            if (!followersCache.containsKey(id)) {
                followersCache.put(id, network.getFollowers(id).get());
            }
        }
        return followersCache.get(id);
    }

}
