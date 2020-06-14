package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class FollowersStatsImpl implements FollowersStats {

    private final UsersCache usersCache;
    private final int parallelism;

    public FollowersStatsImpl(SocialNetwork network) {
        this(network, 2);
    }

    public FollowersStatsImpl(SocialNetwork network, int parallelism) {
        this.usersCache = new UsersCache(network);
        this.parallelism = parallelism;
    }

    @Override
    public Future<Integer> followersCountBy(int userId, int maxDepth, Predicate<UserInfo> filter) {
        var threadPool = new ForkJoinPool(parallelism);
        var futureResult = threadPool.submit(new Task(userId, maxDepth, filter));
        threadPool.shutdown();
        return futureResult;
    }

    private static class UsersCache {

        private final SocialNetwork socialNetwork;
        private final Map<Integer, UserInfo> info = Collections.synchronizedMap(new HashMap<>());
        private final Map<Integer, Collection<Integer>> followersIds = Collections.synchronizedMap(new HashMap<>());

        public UsersCache(SocialNetwork socialNetwork) {
            this.socialNetwork = socialNetwork;
        }

        public UserInfo getInfo(int userId) {
            return info.computeIfAbsent(userId, id -> socialNetwork.getUserInfo(id).join());
        }

        public Collection<Integer> getFollowersIds(int userId) {
            return followersIds.computeIfAbsent(userId, id -> socialNetwork.getFollowers(id).join());
        }
    }

    private class Task extends RecursiveTask<Integer> {

        private final int userId;
        private final int depth;
        private final int maxDepth;
        private final Predicate<UserInfo> filter;
        private final Map<Integer, Object> visitedNodes;

        public Task(int userId, int maxDepth, Predicate<UserInfo> filter) {
            this.userId = userId;
            this.depth = 0;
            this.maxDepth = maxDepth;
            this.filter = filter;
            this.visitedNodes = Collections.synchronizedMap(new HashMap<>());
        }

        public Task(int userId, int depth, int maxDepth, Predicate<UserInfo> filter, Map<Integer, Object> visitedNodes) {
            this.userId = userId;
            this.depth = depth;
            this.maxDepth = maxDepth;
            this.filter = filter;
            this.visitedNodes = visitedNodes;
        }

        @Override
        protected Integer compute() {
            if (depth > maxDepth || visitedNodes.containsKey(userId)) {
                return 0;
            }
            visitedNodes.put(userId, false);
            var filterSatisfied = CompletableFuture.supplyAsync(this::isFilterSatisfied);
            var subTasks = CompletableFuture.supplyAsync(this::prepareSubTasks);
            CompletableFuture.allOf(filterSatisfied, subTasks).join();
            return (filterSatisfied.join() ? 1 : 0) + ForkJoinTask.invokeAll(subTasks.join()).stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        }

        private boolean isFilterSatisfied() {
            var user = usersCache.getInfo(userId);
            return filter.test(user);
        }

        private Collection<Task> prepareSubTasks() {
            return usersCache.getFollowersIds(userId).stream()
                    .map(id -> new Task(id, depth + 1, maxDepth, filter, visitedNodes))
                    .collect(toList());
        }
    }
}
