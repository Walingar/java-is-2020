package impl.network;

import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CachedNetwork implements SocialNetwork {

    private final SocialNetwork network;
    private final Map<Integer, CompletableFuture<UserInfo>> info;
    private final Map<Integer, CompletableFuture<Collection<Integer>>> followersIds;

    public CachedNetwork(SocialNetwork network) {
        this.network = network;
        this.info = new HashMap<>();
        this.followersIds = new HashMap<>();
    }

    @Override
    public CompletableFuture<UserInfo> getUserInfo(int id) {
        return info.computeIfAbsent(id, $ -> network.getUserInfo(id));
    }

    @Override
    public CompletableFuture<Collection<Integer>> getFollowers(int id) {
        return followersIds.computeIfAbsent(id, $ -> network.getFollowers(id));
    }
}
