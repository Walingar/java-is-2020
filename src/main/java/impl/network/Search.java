package impl.network;

import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class Search {

    private final int userId;
    private final int depth;
    private final int maxDepth;
    private final Predicate<UserInfo> filter;
    private final SocialNetwork network;
    private final Set<Integer> visitedNodes;

    public Search(int userId, int maxDepth, Predicate<UserInfo> filter, SocialNetwork network) {
        this(userId, 0, maxDepth, filter, network, ConcurrentHashMap.newKeySet());
    }

    private Search(int userId, int depth, int maxDepth, Predicate<UserInfo> filter,
                   SocialNetwork network, Set<Integer> visitedNodes) {
        this.userId = userId;
        this.depth = depth;
        this.maxDepth = maxDepth;
        this.filter = filter;
        this.network = network;
        this.visitedNodes = visitedNodes;
    }

    public Result execute() {
        if (depth > maxDepth || visitedNodes.contains(userId)) {
            return Result.empty();
        }
        visitedNodes.add(userId);
        CompletableFuture<Boolean> isFilterSatisfied = network.getUserInfo(userId).thenApply(filter::test);
        CompletableFuture<Collection<Search>> subSearches = network.getFollowers(userId).thenApply(ids -> ids.stream()
                .map(id -> new Search(id, depth + 1, maxDepth, filter, network, visitedNodes))
                .collect(toList())
        );
        return new Result(isFilterSatisfied, subSearches);
    }

    public static class Result {

        public final CompletableFuture<Boolean> isFilterSatisfied;
        public final CompletableFuture<Collection<Search>> subSearches;

        public Result(CompletableFuture<Boolean> isFilterSatisfied, CompletableFuture<Collection<Search>> subSearches) {
            this.isFilterSatisfied = isFilterSatisfied;
            this.subSearches = subSearches;
        }

        public static Result empty() {
            return new Result(CompletableFuture.completedFuture(false), CompletableFuture.completedFuture(emptyList()));
        }
    }
}
