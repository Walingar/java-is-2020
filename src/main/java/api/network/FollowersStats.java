package api.network;

import java.util.concurrent.Future;
import java.util.function.Predicate;

public interface FollowersStats {
    Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate);
}
