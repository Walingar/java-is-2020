package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.*;
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
        try {
            return doCountFollowersBy(id, depth, predicate,
                    new ConcurrentHashMap<>() {{
                        put(id, id);
                    }}
//                    new HashSet<>() {{  // WHY DOES Set.of(id) RETURN IMMUTABLE SET?!
//                        add(id);        // This ends up being ugly... Stream.of().collectToSet is also ugly
//                    }}
            );
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(String.format("Something went wrong during followers count (depth %d): %s", depth, e.getMessage()));
            return CompletableFuture.completedFuture(0); //Or null, dunno, not specified =)
        }
    }

    /**
     * Recursively counts followers in a network that satisfy given predicate
     *
     * @param id        id of a user to start count followers
     * @param depth     max depth of recursive travers
     * @param predicate predicate to check
     * @param visited   accumulator of already visited nodes so that we don't count the same user twice
     */
    private Future<Integer> doCountFollowersBy(int id, int depth, Predicate<UserInfo> predicate, ConcurrentHashMap<Integer, Integer> visited)//Set<Integer> visited)
            throws ExecutionException, InterruptedException {

        var userInfo = getUserInfo(id);

        var thisUserValue = predicate.test(userInfo) ? 1 : 0;

        if (depth == 0) {
            return CompletableFuture.completedFuture(thisUserValue); //Wow, this is ugly
        }

        var followersFutures = new ArrayList<Future<Integer>>();
        for (var followerId : getUserFollowers(id)) {

//            This is a leftover from when visited was a Set
//            synchronized (getCacheSyncObject(id)) {
//                if (!visited.contains(followerId)) {
//                    visited.add(followerId);
//                } else {
//                    continue;
//                }
//            }
            // this is thread-safe without additional synchronization, right? right?
            if (visited.putIfAbsent(followerId, followerId) != null) { //very readable tho...
                continue;
            }
            followersFutures.add(doCountFollowersBy(followerId, depth - 1, predicate, visited));
        }

        for (var future : followersFutures) {
            thisUserValue += future.get();
        }

        return CompletableFuture.completedFuture(thisUserValue);
    }

    /**
     * Returns object that can be used as synchronize object around cache access
     * Ok, here for each id we try to get some object to synchronize around, we store those in separate concurrentMap
     * We use (autoboxed)Integer as map key, but since checking for already being there is based on equals()
     * we will not create separate locks for different boxed Integer instances.
     * Here we store the very first autoboxed Integer as an object to sync around
     * This is needed so that putIfAbsent is atomic and we don't create new objects per call:
     * userIdLocks.putIfAbsent(id, new Object()) <- creates new Object before call even if we don't need it
     * if(userIdLocks.containsKey(id) userIdLocks.put(id, new Object) <- not atomic
     *
     * @param id int id of synch object.
     * @return Object (Integer really) unique object to synchronize around to access caches.
     */
    private Object getCacheSyncObject(final Integer id) {
        userIdLocks.putIfAbsent(id, id);
        return userIdLocks.get(id);
    }

    private UserInfo getUserInfo(int id) throws ExecutionException, InterruptedException {
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
