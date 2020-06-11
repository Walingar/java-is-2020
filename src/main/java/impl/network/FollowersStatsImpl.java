package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;

public class FollowersStatsImpl implements FollowersStats {

    private final SocialNetwork network;

    public FollowersStatsImpl(SocialNetwork network) {
        this.network = network;
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return new FutureTask<Integer>(() -> {
            return 0;
        });
    }
}
