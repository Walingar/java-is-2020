package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import static java.util.Collections.singleton;

public class FollowersStatsImpl implements FollowersStats {

    private final SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return CompletableFuture.supplyAsync(() -> {
            CompletableFuture<Integer> total = CompletableFuture.completedFuture(0);
            Search initialSearch = new Search(id, depth, predicate, network);
            Queue<Search> pendingSearches = new LinkedList<>(singleton(initialSearch));
            while (!pendingSearches.isEmpty()) {
                Search search = pendingSearches.remove();
                Search.Result result = search.execute();
                pendingSearches.addAll(result.subSearches.join());
                total = result.isFilterSatisfied.thenCombine(total, (satisfied, count) -> satisfied ? count + 1 : count);
            }
            return total.join();
        });
    }
}
