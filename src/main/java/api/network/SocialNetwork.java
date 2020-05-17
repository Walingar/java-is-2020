package api.network;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface SocialNetwork {
    CompletableFuture<Collection<Integer>> getFollowers(int id);

    CompletableFuture<UserInfo> getUserInfo(int id);
}
