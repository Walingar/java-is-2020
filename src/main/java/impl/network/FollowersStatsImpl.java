package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

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
        return search(new Search(id, depth, predicate, network));
    }

    private CompletableFuture<Integer> search(Search search) {
        Search.Result result = search.execute();
        CompletableFuture<Integer> searchCount = result.isFilterSatisfied.thenApply(satisfied -> satisfied ? 1 : 0);
        CompletableFuture<Integer> subSearchesCount = result.subSearches.thenCompose(subSearches -> subSearches.stream()
                .map(this::search)
                .reduce((a, b) -> a.thenCombine(b, Integer::sum))
                .orElse(CompletableFuture.completedFuture(0))
        );
        return searchCount.thenCombine(subSearchesCount, Integer::sum);
    }
}
