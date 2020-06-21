package impl.network;

import api.network.FollowersStats;
import api.network.SocialNetwork;

public class FollowersStatsFactory {
    public static FollowersStats getInstance(SocialNetwork network) {
        return new FollowersStatsRealisation(network);
    }
}
