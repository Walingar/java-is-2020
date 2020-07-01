package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {

  private SocialNetwork network;

  public FollowersStatsImpl(SocialNetwork network) {
    this.network = network;
  }

  @Override
  public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
    return followersCountBy(id, depth, predicate, new ConcurrentHashMap<>() {{
      put(id, id);
    }});
  }

  private CompletableFuture<Integer> followersCountBy(int id, int depth,
      Predicate<UserInfo> predicate,
      ConcurrentMap<Integer, Integer> visited) {
    CompletableFuture<Integer> userInfo = network.getUserInfo(id)
        .thenApply(user -> predicate.test(user) ? 1 : 0);

    if (depth == 0) {
      return userInfo;
    }

    return userInfo.thenCombine(
        network
            .getFollowers(id)
            .thenCompose(findFollowers(depth, predicate, visited)),
        Integer::sum
    );
  }

  private Function<Collection<Integer>, CompletionStage<Integer>> findFollowers(int depth,
      Predicate<UserInfo> predicate,
      ConcurrentMap<Integer, Integer> visited) {
    return followers -> followers.stream()
        .map(goDeeper(depth, predicate, visited))
        .reduce((x, y) -> x.thenCombine(y, Integer::sum))
        .orElse(CompletableFuture.completedFuture(0));
  }

  private Function<Integer, CompletableFuture<Integer>> goDeeper(int depth,
      Predicate<UserInfo> predicate, ConcurrentMap<Integer, Integer> visited) {
    return follower -> {
      if (Objects.isNull(visited.putIfAbsent(follower, follower))) {
        return followersCountBy(follower, depth - 1, predicate, visited);
      }
      return CompletableFuture.completedFuture(0);
    };
  }
}