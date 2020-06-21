package impl.network;



import api.network.FollowersStats;
import api.network.SocialNetwork;
import api.network.UserInfo;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FollowersStatsRealisation implements FollowersStats {
    private SocialNetwork network;
    private ExecutorService executor;

    public FollowersStatsRealisation(SocialNetwork network){
        this.network = network;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public Future<Integer> followersCountBy(int id, int depth, Predicate<UserInfo> predicate) {
        return executor.submit(() -> {
            ArrayDeque<Integer> activeUsersToView = new ArrayDeque<>();
            LinkedHashSet<Integer> allUsers = new LinkedHashSet<>();

            activeUsersToView.add(id);
            allUsers.add(id);
            int firstIndex = 1;

            for (int i = 0; i < depth; ++i){
                while (!activeUsersToView.isEmpty()){
                    allUsers.addAll(network.getFollowers(activeUsersToView.poll()).get());
                }

                int lastIndex = allUsers.size(); // new users

                List<Integer> list = new ArrayList<>(allUsers);
                activeUsersToView.addAll(list.subList(firstIndex, lastIndex));

                firstIndex = lastIndex;
            }

            return (int) allUsers.stream().filter(studentId -> {
                try {
                    return predicate.test(network.getUserInfo(studentId).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                };
                return false;
            }).count();
        });
    };

}

